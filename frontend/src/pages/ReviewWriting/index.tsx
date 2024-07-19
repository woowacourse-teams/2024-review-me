import KeywordButton from './components/KeywordButton';
import * as S from './styles';

import { postReviewApi } from '@/apis/review';
import ClockLogo from '@/assets/clock.svg';
import GithubLogo from '@/assets/githubLogo.svg';
import Button from '@/components/common/Button';
import { ReviewData } from '@/types';

const ReviewWritingPage = () => {
  const handleSubmitReview = async (event: React.FormEvent) => {
    event.preventDefault();

    const reviewData: ReviewData = {
      reviewerId: 1,
      reviewerGroupId: 1,
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
      ],
      selectedKeywordIds: [
        (event.target as any).keyword1.checked ? 1 : null,
        (event.target as any).keyword2.checked ? 2 : null,
        (event.target as any).keyword3.checked ? 3 : null,
      ].filter(Boolean) as number[],
    };

    //console.log(reviewData);
    try {
      await postReviewApi({ reviewData });
      console.log('Review submitted successfully');
    } catch (error) {
      console.error('Failed to submit review:', error);
    }
  };

  return (
    <S.ReviewWritingPage onSubmit={handleSubmitReview}>
      <S.ReviewFormHeader>
        <S.LogoImage src={GithubLogo} alt="깃허브 로고" />
        <S.InfoContainer>
          <S.ProjectName>2024-review-me</S.ProjectName>
          <S.ReviewInfo>
            <S.Reviewee>
              <span>chysis</span>님을 리뷰해주세요!
            </S.Reviewee>
            <S.ReviewExpirationDate>
              <img src={ClockLogo} alt="시계" />
              리뷰 마감일: 2024/07/22
            </S.ReviewExpirationDate>
          </S.ReviewInfo>
        </S.InfoContainer>
      </S.ReviewFormHeader>
      <S.ReviewFormMain>
        <S.ReviewContainer>
          <S.ReviewItem>
            <S.ReviewQuestion>1. 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.</S.ReviewQuestion>
            <S.ReviewTextarea name="question1" id="question1"></S.ReviewTextarea>
          </S.ReviewItem>
          <S.ReviewItem>
            <S.ReviewQuestion>2. 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.</S.ReviewQuestion>
            <S.ReviewTextarea name="question2" id="question2"></S.ReviewTextarea>
          </S.ReviewItem>
          <S.ReviewItem>
            <S.ReviewQuestion>3. 팀 동료로 근무한다면 같이 일 하고 싶은 개발자인가요?</S.ReviewQuestion>
            <S.ReviewTextarea name="question2" id="question2"></S.ReviewTextarea>
          </S.ReviewItem>
        </S.ReviewContainer>
        <S.KeywordContainer>
          <S.KeywordTitle>키워드</S.KeywordTitle>
          <S.KeywordList>
            <KeywordButton isSelected={false}>잘 먹어요</KeywordButton>
            <KeywordButton isSelected={true}>성실해요</KeywordButton>
          </S.KeywordList>
        </S.KeywordContainer>
        <S.ButtonContainer>
          <Button buttonType="secondary" text="저장" />
          <Button buttonType="primary" text="제출" />
        </S.ButtonContainer>
      </S.ReviewFormMain>
    </S.ReviewWritingPage>
  );
};

export default ReviewWritingPage;
