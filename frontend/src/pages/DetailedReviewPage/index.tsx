import { useState, useEffect } from 'react';
import { useLocation, useParams } from 'react-router';

import { ReviewComment } from '@/components';
import { DetailReviewData } from '@/types';

import { getDetailedReviewApi } from '../../apis/review';

import KeywordSection from './components/KeywordSection';
import ReviewDescription from './components/ReviewDescription';
import ReviewSection from './components/ReviewSection';
import * as S from './styles';

const COMMENT = 'VITE 쓰고 싶다.';

const DetailedReviewPage = () => {
  const { id: reviewId } = useParams();
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const memberId = searchParams.get('memberId');

  const [detailedReview, setDetailedReview] = useState<DetailReviewData>();
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>('');

  const fetch = async () => {
    if (!reviewId) return;
    try {
      setIsLoading(true);
      const result = await getDetailedReviewApi({ reviewId: Number(reviewId), memberId: Number(memberId) });

      setDetailedReview(result);
      setErrorMessage('');
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetch();
  }, []);

  if (isLoading) return <div>Loading...</div>;

  if (errorMessage) return <div>Error: {errorMessage}</div>;
  if (!detailedReview) return <div>Error: 상세보기 리뷰 데이터를 가져올 수 없어요.</div>;

  return (
    <S.DetailedReviewPage>
      <ReviewDescription
        projectName={detailedReview.reviewerGroup.name}
        date={new Date(detailedReview.reviewerGroup.deadline)}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <ReviewComment comment={COMMENT} />
      {detailedReview.contents.map((item, index) => (
        <ReviewSection question={item.question} answer={item.answer} key={index} index={index} />
      ))}
      <KeywordSection keywords={detailedReview.keywords} index={detailedReview.contents.length} />
    </S.DetailedReviewPage>
  );
};

export default DetailedReviewPage;
