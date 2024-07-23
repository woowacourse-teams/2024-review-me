import KeywordButton from './components/KeywordButton';
import * as S from './styles';

import { postReviewApi } from '@/apis/review';
import ClockLogo from '@/assets/clock.svg';
import GithubLogo from '@/assets/githubLogo.svg';
import Button from '@/components/common/Button';
import { ReviewData } from '@/types';
import { REVIEW } from './../../constants/review';
import RevieweeComment from './components/RevieweeComment';
import ReviewItem from './components/ReviewItem';

const ReviewWritingPage = () => {
  const handleSubmitReview = async (event: React.FormEvent) => {
    event.preventDefault();

    // NOTE: 모킹 데이터
    const reviewData: ReviewData = {
      reviewerId: 8,
      reviewerGroupId: 5,
      contents: [
        {
          order: 1,
          question: '1. 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.',
          answer: (event.target as any).question1.value,
        },
        {
          order: 2,
          question: '2. 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.',

          answer: (event.target as any).question2.value,
        },
        {
          order: 3,
          question: '3. 마지막 질문',

          answer: (event.target as any).question2.value,
        },
      ],
      selectedKeywordIds: [1, 2],
    };

    try {
      await postReviewApi({ reviewData });
    } catch (error) {
      console.error('Failed to submit review:', error);
    }
  };

  return (
    <S.ReviewWritingPage onSubmit={handleSubmitReview}>
      <S.ReviewFormHeader>
        <S.ReviewInfoContainer>
          <S.LogoImage src={GithubLogo} alt="깃허브 로고" />
          <S.Container>
            <S.ProjectName>2024-review-me</S.ProjectName>
            <S.ReviewInfo>
              <S.Reviewee>
                <span>chysis</span>님을 리뷰해주세요!
              </S.Reviewee>
              <S.ReviewExpirationDate>
                <img src={ClockLogo} alt="시계" />
                리뷰 마감일: 2024-08-05
              </S.ReviewExpirationDate>
            </S.ReviewInfo>
          </S.Container>
        </S.ReviewInfoContainer>
        <RevieweeComment text={''}></RevieweeComment>
      </S.ReviewFormHeader>
      <S.ReviewFormMain>
        <S.ReviewContainer>
          {REVIEW.questionList.map((question, idx) => {
            return <ReviewItem question={question} key={idx} />;
          })}
        </S.ReviewContainer>
        <S.KeywordContainer>
          <S.KeywordTitle>키워드</S.KeywordTitle>
          <S.KeywordList>
            <KeywordButton isSelected={false}>잘 먹어요</KeywordButton>
            <KeywordButton isSelected={true}>성실해요</KeywordButton>
          </S.KeywordList>
        </S.KeywordContainer>
        <S.ButtonContainer>
          {/* <Button buttonType="secondary" text="저장" /> */}
          <Button buttonType="primary" text="제출" />
        </S.ButtonContainer>
      </S.ReviewFormMain>
    </S.ReviewWritingPage>
  );
};

export default ReviewWritingPage;
