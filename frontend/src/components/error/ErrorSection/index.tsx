import AlertTriangle from '@/assets/alertTriangle.svg';
import HomeIcon from '@/assets/home.svg';
import ReloadIcon from '@/assets/reload.svg';
import { Button } from '@/components';
import { ButtonType } from '@/types/styles';

import * as S from './styles';

interface ErrorSectionProps {
  errorMessage: string;
  handleReload: () => void;
  handleGoHome: () => void;
}

const ErrorSection = ({ errorMessage, handleReload, handleGoHome }: ErrorSectionProps) => {
  const buttons = [
    {
      buttonType: 'primary' as ButtonType,
      text: '새로고침하기',
      image: ReloadIcon,
      imageDescription: '새로고침 이미지',
      onClick: handleReload,
    },
    {
      buttonType: 'secondary' as ButtonType,
      text: '홈으로 이동하기',
      image: HomeIcon,
      imageDescription: '홈 이미지',
      onClick: handleGoHome,
    },
  ];

  return (
    <S.Layout>
      <S.ErrorLogoWrapper>
        <img src={AlertTriangle} alt="에러 로고" />
      </S.ErrorLogoWrapper>
      <S.ErrorMessage>{errorMessage}</S.ErrorMessage>
      <S.Container>
        {buttons.map((button, index) => (
          <S.ButtonContainer key={index}>
            <Button
              buttonType={button.buttonType}
              text={button.text}
              image={button.image}
              imageDescription={button.imageDescription}
              onClick={button.onClick}
            />
          </S.ButtonContainer>
        ))}
      </S.Container>
    </S.Layout>
  );
};

export default ErrorSection;
