import { useSuspenseQuery } from '@tanstack/react-query';
// import { useCallback, useRef } from 'react';
import { useNavigate } from 'react-router';

import { getReviewListApi } from '@/apis/review';
// import { useReviewPreviewList } from '@/hooks/useReviewPreviewList';
import ReviewPreviewCard from '@/components/ReviewPreviewCard';
import { REVIEW_QUERY_KEYS } from '@/constants';
import LoadingPage from '@/pages/LoadingPage';

import ReviewInfoSection from '../ReviewInfoSection';
import SearchSection from '../SearchSection';

import * as S from './styles';

const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
const OPTIONS = ['최신순', '오래된순'];
const MEMBER_ID = 2;

interface PageContentsProps {
  groupAccessCode: string;
}
const PageContents = ({ groupAccessCode }: PageContentsProps) => {
  const navigate = useNavigate();
  // NOTE: 무한스크롤 코드 일단 주석 처리
  // const { data, fetchNextPage, hasNextPage, isLoading, error } = useReviewPreviewList();

  const { data, isLoading, error } = useSuspenseQuery({
    queryKey: [REVIEW_QUERY_KEYS.reviews],
    queryFn: () => getReviewListApi(groupAccessCode),
  });

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
  };

  // const observer = useRef<IntersectionObserver | null>(null);

  // const lastReviewElementRef = useCallback(
  //   (node: HTMLElement | null) => {
  //     if (isLoading) return <LoadingPage />;
  //     if (observer.current) observer.current.disconnect();

  //     observer.current = new IntersectionObserver((entries) => {
  //       if (entries[0].isIntersecting && hasNextPage) {
  //         fetchNextPage();
  //       }
  //     });

  //     if (node) observer.current.observe(node);
  //   },
  //   [isLoading, fetchNextPage, hasNextPage],
  // );

  return (
    <>
      <S.Layout>
        <ReviewInfoSection
          projectName={data.projectName}
          revieweeName={`${data.revieweeName}님에게 달린 리뷰입니다!`}
        />
        <SearchSection handleChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} />
        <S.ReviewSection>
          {isLoading && <LoadingPage />}
          {error && <p>{error.message}</p>}
          <S.ReviewSection>
            {isLoading && <LoadingPage />}
            {error && <p>{error.message}</p>}
            {data &&
              data.reviews.map((review) => (
                // const isLastElement = pageIndex === data.pages.length - 1 && reviewIndex === page.reviews.length - 1;
                <div key={review.id} onClick={() => handleReviewClick(review.id)}>
                  <ReviewPreviewCard
                    id={review.id}
                    projectName={data.projectName}
                    createdAt={review.createdAt}
                    contentPreview={review.contentPreview}
                    keywords={review.keywords}
                  />
                  {/* <div ref={isLastElement ? lastReviewElementRef : null}></div> */}
                </div>
              ))}
          </S.ReviewSection>
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default PageContents;
