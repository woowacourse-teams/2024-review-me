import { useState, useEffect } from 'react';
import { useParams } from 'react-router';

import { ReviewComment } from '@/components';
import { DetailReviewData } from '@/types';

import { getDetailedReviewApi } from '../../apis/review';

import KeywordSection from './components/KeywordSection';
import ReviewDescription from './components/ReviewDescription';
import ReviewSection from './components/ReviewSection';
import * as S from './styles';

const ANSWER =
  '림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. \n 바람은 여전히 그를 감싸며, 그의 마음 속 깊은 곳에 있는 꿈과 희망을 불러일으켰습니다.\n 림순은 미소 지으며 앞으로 나아갔습니다.림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. 림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. \n 바람은 여전히 그를 감싸며, 그의 마음 속 깊은 곳에 있는 꿈과 희망을 불러일으켰습니다.\n 림순은 미소 지으며 앞으로 나아갔습니다.림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. ';

const MOCK_DATA: DetailReviewData = {
  id: 123456,
  createdAt: new Date('2024-07-16'),
  reviewerGroup: {
    id: 123456,
    name: 'review-me',
    deadline: new Date('2024-07-01'),
    reviewee: {
      id: 78910,
      name: '바다',
    },
  },
  contents: [
    {
      id: 23456,
      question: '[공개] 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.',
      answer: ANSWER,
    },
    { id: 567810, question: '[공개] 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.', answer: ANSWER },
    { id: 98761, question: '[비공개] 팀 동료로 근무한다면 같이 일 하고 싶은 개발자인가요?', answer: ANSWER },
  ],
  keywords: [
    { id: 1, detail: '친절해요' },
    { id: 12, detail: '친절합니다!' },
    { id: 11, detail: '친절해요요요요요' },
    { id: 14, detail: '친절해해해해해' },
    { id: 18, detail: '친절해요요용' },
  ],
};

const COMMENT = 'VITE 쓰고 싶다.';

const DetailedReviewPage = () => {
  const { id: reviewId } = useParams();
  const [detailReview, setDetailReview] = useState<DetailReviewData>(MOCK_DATA);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const fetch = async () => {
    if (!reviewId) return;
    try {
      setIsLoading(true);
      const result = await getDetailedReviewApi({ reviewId: Number(reviewId) });

      setDetailReview(result);
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

  return (
    <S.DetailedReviewPage>
      <ReviewDescription
        projectName={detailReview.reviewerGroup.name}
        date={detailReview.reviewerGroup.deadline}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <ReviewComment comment={COMMENT} />
      {detailReview.contents.map((item, index) => (
        <ReviewSection question={item.question} answer={item.answer} key={index} index={index} />
      ))}
      <KeywordSection keywords={detailReview.keywords} index={detailReview.contents.length} />
    </S.DetailedReviewPage>
  );
};

export default DetailedReviewPage;
