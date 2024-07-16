import { postReviewApi } from '../../apis/review';
import { ReviewData } from '@/types';
import * as S from './styles';
import MenuIcon from '../../assets/menu.svg';

const ReviewWriting = () => {
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
    <S.ReviewWritingForm onSubmit={handleSubmitReview}>
      <S.InfoContainer>
        <S.ProjectName>2024-review-me</S.ProjectName>
        <S.ReviewInfo>
          <div>chysis 님을 리뷰해주세요!</div>
          <div>리뷰 마감일: 2024/07/22</div>
        </S.ReviewInfo>
      </S.InfoContainer>
      <S.ReviewList>
        <S.ReviewItem>
          <div>1. 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.</div>
          <S.ReviewTextarea name="question1" id="question1"></S.ReviewTextarea>
        </S.ReviewItem>
        <S.ReviewItem>
          <div>2. 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.</div>
          <S.ReviewTextarea name="question2" id="question2"></S.ReviewTextarea>
        </S.ReviewItem>
        <S.ReviewItem>
          <div>3. 팀 동료로 근무한다면 같이 일 하고 싶은 개발자인가요?</div>
          <S.ReviewTextarea name="question2" id="question2"></S.ReviewTextarea>
        </S.ReviewItem>
      </S.ReviewList>
      <S.KeywordContainer>
        <S.KeywordTitle>키워드</S.KeywordTitle>
        <S.KeywordList>
          <S.KeywordItem>
            <input type="checkbox" id="keyword1" name="keyword1" value="키워드 1" />
            <label htmlFor="keyword1">성실해요</label>
          </S.KeywordItem>
          <S.KeywordItem>
            <input type="checkbox" id="keyword2" name="keyword2" value="키워드 2" />
            <label htmlFor="keyword2">활발해요</label>
          </S.KeywordItem>
          <S.KeywordItem>
            <input type="checkbox" id="keyword3" name="keyword3" value="키워드 3" />
            <label htmlFor="keyword3">잘 먹어요</label>
          </S.KeywordItem>
        </S.KeywordList>
      </S.KeywordContainer>
      <S.ButtonContainer>
        <S.Button type="button">저장</S.Button>
        <S.Button type="submit">제출</S.Button>
      </S.ButtonContainer>
    </S.ReviewWritingForm>
  );
};

export default ReviewWriting;
