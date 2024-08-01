import AlertTriangle from '@/assets/alertTriangle.svg';
import HomeIcon from '@/assets/home.svg';
import ReloadIcon from '@/assets/reload.svg';
import { Button } from '@/components';
import { ButtonStyleType } from '@/types/styles';

import * as S from './styles';

interface ErrorSectionProps {
  errorMessage: string;
  handleReload: () => void;
  handleGoHome: () => void;
}

interface ErrorSectionButton {
  buttonType: ButtonStyleType;
  key: string;
  text: string;
  /* eslint-disable */
  imageSrc: any;
  imageDescription: string;
  onClick: () => void;
}

const ErrorSection = ({ errorMessage, handleReload, handleGoHome }: ErrorSectionProps) => {
  const buttonList: ErrorSectionButton[] = [
    {
      buttonType: 'primary',
      key: 'refreshButton',
      text: '새로고침하기',
      imageSrc: ReloadIcon,
      imageDescription: '새로고침 이미지',
      onClick: handleReload,
    },
    {
      buttonType: 'secondary' as ButtonType,
      key: 'homeButton',
      text: '홈으로 이동하기',
      imageSrc: HomeIcon,
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
        {buttonList.map((button) => (
          <Button
            type="button"
            key={button.key}
            buttonType={button.buttonType}
            text={button.text}
            image={button.imageSrc}
            imageDescription={button.imageDescription}
            onClick={button.onClick}
          />
        ))}
      </S.Container>
    </S.Layout>
  );
};

export default ErrorSection;
