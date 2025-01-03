import { URLGeneratorForm } from '@/pages/HomePage/components';

import * as S from './styles';

const TITLE = '생성한 리뷰 링크를 확인해보세요';
const SUBTITLE = '클릭하면 해당 프로젝트의 리뷰 목록으로 이동해요';

const ReviewLinkDashboard = () => {
  return (
    <S.Layout>
      <S.LeftContainer>
        <URLGeneratorForm />
      </S.LeftContainer>
      <S.Separator />
      <S.RightContainer>
        <S.TitleWrapper>
          <h2>{TITLE}</h2>
          <S.SubTitle>{SUBTITLE}</S.SubTitle>
        </S.TitleWrapper>
        <S.ReviewLinkList>{/* TODO: ReviewCard 컴포넌트 추가 */}</S.ReviewLinkList>
      </S.RightContainer>
    </S.Layout>
  );
};

export default ReviewLinkDashboard;
