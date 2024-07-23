import { DetailReviewData } from '@/types';
import ReviewViewSection from './components/ReviewViewSection';
import ReviewDescription from './components/ReviewDescription';

import { useState, useEffect } from 'react';
import { getDetailedReviewApi } from '../../apis/review';

const ANSWER =
  '림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. \n 바람은 여전히 그를 감싸며, 그의 마음 속 깊은 곳에 있는 꿈과 희망을 불러일으켰습니다.\n 림순은 미소 지으며 앞으로 나아갔습니다.림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. ';

const MOCK_DATA: DetailReviewData = {
  id: 123456,
  reviewer: {
    memberId: 123456,
    name: '올리',
  },
  createdAt: new Date('2024-07-16'),
  reviewerGroup: {
    groupId: 123456,
    name: 'review-me',
  },
  contents: [
    {
      question: '1. [공개] 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.',
      answer: ANSWER,
    },
    { question: '2. [공개] 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.', answer: ANSWER },
    { question: '3. [비공개] 팀 동료로 근무한다면 같이 일 하고 싶은 개발자인가요?', answer: ANSWER },
  ],
  keywords: [{ id: 1, detail: '친절해요' }],
};

const DetailedReviewPage = ({}) => {
  const [detailReview, setDetailReview] = useState<DetailReviewData>(MOCK_DATA);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const fetch = async () => {
    try {
      setIsLoading(true);
      getDetailedReviewApi({ reviewId: 4 }).then((result) => {
        setDetailReview(result);
        setErrorMessage('');
      });
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
    <>
      <ReviewDescription
        projectName={detailReview.reviewerGroup.name}
        // NOTE: 프론트에서는 리뷰 작성일을 보여주지만,
        // 현재 서버에서 오는 데이터가 deadline인 관계로 (속성 이름, value의 성격 모두 다름)
        // 임의의 Date 객체로 하드코딩한 상태임
        createdAt={new Date('2024-01-22')}
        isLock={true}
      />
      {detailReview.contents.map((item, index) => (
        <ReviewViewSection question={item.question} answer={item.answer} key={index} />
      ))}
    </>
  );
};

export default DetailedReviewPage;
