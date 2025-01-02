import ReviewCard from '@/components/ReviewCard';

import { URLGeneratorForm } from '../HomePage/components';

import * as S from './styles';

const ReviewLinkManagementPage = () => {
  return (
    <S.Layout>
      <S.LeftContainer>
        <URLGeneratorForm />
      </S.LeftContainer>
      <S.Separator />
      <S.RightContainer>
        <S.TitleWrapper>
          <h2>생성한 리뷰 링크를 확인해보세요</h2>
          <S.SubTitle>클릭하면 해당 프로젝트의 리뷰 목록으로 이동해요</S.SubTitle>
        </S.TitleWrapper>
        <S.ReviewLinkList>
          <ReviewCard createdAt={''} contentPreview={''} categories={[]} handleClick={() => {}} />
        </S.ReviewLinkList>
      </S.RightContainer>
    </S.Layout>
  );
};

export default ReviewLinkManagementPage;
