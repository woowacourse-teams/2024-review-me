import { DetailReviewData } from '@/types';
import ReviewViewSection from './components/ReviewViewSection';
import ReviewDescription from './components/ReviewDescription';
import { DetailedReview, DetailReviewOuter, Header } from './styles';
import { css } from '@emotion/react';

const ANSWER =
  '림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. \n 바람은 여전히 그를 감싸며, 그의 마음 속 깊은 곳에 있는 꿈과 희망을 불러일으켰습니다.\n 림순은 미소 지으며 앞으로 나아갔습니다.림순의 바람은 그윽한 산들바람처럼 잔잔하게 흘러갔습니다. \n 눈부신 햇살이 그의 어깨를 감싸며, 푸른 하늘 아래 펼쳐진 들판을 바라보았습니다.\n 그의 마음은 자연의 아름다움 속에서 평온을 찾았고, 그 순간마다 삶의 소중함을 느꼈습니다.\n 그는 늘 그러한 순간들을 기억하며, 미래의 나날들을 기대했습니다. ';

const MOCK_DATA: DetailReviewData = {
  reviewer: {
    memberId: 123456,
    name: '올리',
  },
  reviewGroup: {
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
};

const DetailedReviewPage = ({}) => {
  return (
    <DetailReviewOuter>
      <Header>Header</Header>
      <DetailedReview>
        <ReviewDescription projectName={MOCK_DATA.reviewGroup.name} dateCreated="2024-07-15" isLock={true} />
        {MOCK_DATA.contents.map((item) => (
          <ReviewViewSection question={item.question} answer={item.answer} />
        ))}
      </DetailedReview>
    </DetailReviewOuter>
  );
};

export default DetailedReviewPage;
