import * as S from './styles';

interface ReviewLinkManagementLayoutProps {
  leftContent: React.ReactNode;
  rightContent: React.ReactNode;
}

const ReviewLinkManagementLayout = ({ leftContent, rightContent }: ReviewLinkManagementLayoutProps) => {
  return (
    <S.Layout>
      <S.LeftWrapper>{leftContent}</S.LeftWrapper>
      <S.Separator />
      <S.RightWrapper>{rightContent}</S.RightWrapper>
    </S.Layout>
  );
};

export default ReviewLinkManagementLayout;
