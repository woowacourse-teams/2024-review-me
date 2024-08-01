import { useCallback, useRef } from 'react';
import { useNavigate } from 'react-router';

import ReviewPreviewCard from '@/components/ReviewPreviewCard';
import { useReviewPreviewList } from '@/hooks/useReviewPreviewList';
import { ReviewPreview } from '@/types';

import LoadingPage from '../LoadingPage';

import ReviewInfoSection from './components/ReviewInfoSection';
import SearchSection from './components/SearchSection';
import * as S from './styles';

const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
const OPTIONS = ['최신순', '오래된순'];
const MEMBER_ID = 2;

const ReviewPreviewListPage = () => {
  const navigate = useNavigate();
  const { data, fetchNextPage, hasNextPage, isLoading, error } = useReviewPreviewList();

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
  };

  const observer = useRef<IntersectionObserver | null>(null);

  const lastReviewElementRef = useCallback(
    (node: HTMLElement | null) => {
      if (isLoading) return <LoadingPage />;
      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });

      if (node) observer.current.observe(node);
    },
    [isLoading, fetchNextPage, hasNextPage],
  );

  return (
    <>
      <S.Layout>
        <ReviewInfoSection
          projectName={data?.pages[0].projectName}
          revieweeName={`${data?.pages[0].revieweeName}님에게 달린 리뷰입니다!`}
        />
        <SearchSection handleChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} />
        <S.ReviewSection>
          {isLoading && <LoadingPage />}
          {error && <p>{error.message}</p>}
          {data &&
            data.pages.map((page, pageIndex) =>
              page.reviews.map((item: ReviewPreview, reviewIndex: number) => {
                const isLastElement = pageIndex === data.pages.length - 1 && reviewIndex === page.reviews.length - 1;
                return (
                  <div key={item.id} onClick={() => handleReviewClick(item.id)}>
                    <ReviewPreviewCard
                      id={item.id}
                      projectName={page.projectName}
                      createdAt={item.createdAt}
                      contentPreview={item.contentPreview}
                      keywords={item.keywords}
                    />
                    <div ref={isLastElement ? lastReviewElementRef : null}></div>
                  </div>
                );
              }),
            )}
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default ReviewPreviewListPage;
