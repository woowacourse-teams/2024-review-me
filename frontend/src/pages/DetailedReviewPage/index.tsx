import { useState, useEffect } from 'react';
import { useLocation, useParams } from 'react-router';

import { ReviewComment } from '@/components';
import { DetailReviewData } from '@/types';

import { getDetailedReviewApi } from '../../apis/review';
import LoadingPage from '../LoadingPage';

import KeywordSection from './components/KeywordSection';
import ReviewDescription from './components/ReviewDescription';
import ReviewSection from './components/ReviewSection/index';
import * as S from './styles';

const DetailedReviewPage = () => {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const memberId = queryParams.get('memberId');

  const [detailedReview, setDetailReview] = useState<DetailReviewData>();
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>('');

  useEffect(() => {
    const fetchReview = async () => {
      try {
        setIsLoading(true);

        const result = await getDetailedReviewApi({ reviewId: Number(id), memberId: Number(memberId) });
        setDetailReview(result);
        setErrorMessage('');
      } catch (error) {
        setErrorMessage('리뷰를 불러오는 데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchReview();
  }, [id]);

  if (isLoading) return <LoadingPage />;

  if (errorMessage) return <div>Error: {errorMessage}</div>;
  if (!detailedReview) return <div>Error: 상세보기 리뷰 데이터를 가져올 수 없어요.</div>;

  return (
    <S.DetailedReviewPage>
      <ReviewDescription
        projectName={detailedReview.reviewerGroup.name}
        date={new Date(detailedReview.createdAt)}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <ReviewComment comment={detailedReview.reviewerGroup.description} />
      {detailedReview.reviews.map((item, index) => (
        <ReviewSection question={item.question} answer={item.answer} key={index} index={index} />
      ))}
      <KeywordSection keywords={detailedReview.keywords} index={detailedReview.reviews.length} />
    </S.DetailedReviewPage>
  );
};

export default DetailedReviewPage;
