import * as S from './styles';
import menu from '../../assets/menu.svg';

const ReviewWriting = () => {
  return (
    <S.Container>
      <S.Header>
        <div>햄버거</div>
        <img src={menu}>로고</img>
        <div>리뷰작성</div>
      </S.Header>
      <S.Main>
        <S.ReviewWritingForm>
          <S.InfoContainer>
            <S.ProjectName>woowacourse-teams/2024-review-me</S.ProjectName>
            <S.ReviewInfo>
              <div>chysis 님을 리뷰해주세요!</div>
              <div>리뷰 유효기간: 2024/07/22</div>
            </S.ReviewInfo>
          </S.InfoContainer>
          <S.ReviewList>
            <S.ReviewItem>
              <div>1. 동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.</div>
              <S.ReviewTextarea name="question1" id="question1"></S.ReviewTextarea>
            </S.ReviewItem>
            <div>
              <div>2. 동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.</div>
              <S.ReviewTextarea name="question2" id="question2"></S.ReviewTextarea>
            </div>
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
      </S.Main>
    </S.Container>
  );
};

export default ReviewWriting;
