import AlertTriangleIcon from '@/assets/alertTriangle.svg';
import PrimaryHomeIcon from '@/assets/primaryHome.svg';
import PrimaryReloadIcon from '@/assets/primaryReload.svg';
import WhiteHomeIcon from '@/assets/whiteHome.svg';
import WhiteReloadIcon from '@/assets/whiteReload.svg';
import { Button } from '@/components';
import { ROUTE_ERROR_MESSAGE } from '@/constants';
import { ButtonStyleType } from '@/types/styles';

import * as S from './styles';

export interface ErrorSectionProps {
  errorMessage: string;
  handleReload: () => void;
  handleGoOtherPage: () => void;
  errorType?: 'notFound' | 'invalidAccess';
}

export interface ErrorSectionButton {
  buttonType: ButtonStyleType;
  key: string;
  text: string;
  /* eslint-disable */
  imageSrc: any;
  imageDescription: string;
  onClick: () => void;
}

const ErrorSection = ({ errorMessage, handleReload, handleGoOtherPage, errorType = 'notFound' }: ErrorSectionProps) => {
  const isGoHomeButtonFirst = errorMessage === ROUTE_ERROR_MESSAGE;
  // errorMessage에 따른 커스텀
  const buttonList: ErrorSectionButton[] = [];

  if (errorType === 'notFound') {
    buttonList.push({
      buttonType: isGoHomeButtonFirst ? 'secondary' : 'primary',
      key: 'refreshButton',
      text: '새로고침하기',
      imageSrc: isGoHomeButtonFirst ? PrimaryReloadIcon : WhiteReloadIcon,
      imageDescription: '새로고침 이미지',
      onClick: handleReload,
    });
  }

  buttonList.push({
    buttonType: errorType === 'invalidAccess' ? 'primary' : isGoHomeButtonFirst ? 'primary' : 'secondary',
    key: 'homeButton',
    text: '홈으로 이동하기',
    imageSrc: errorType === 'invalidAccess' ? WhiteHomeIcon : isGoHomeButtonFirst ? WhiteHomeIcon : PrimaryHomeIcon,
    imageDescription: '홈 이미지',
    onClick: handleGoOtherPage,
  });

  const errorSectionButtonList = isGoHomeButtonFirst ? buttonList.reverse() : buttonList;

  return (
    <S.Layout>
      <S.ErrorLogoWrapper>
        <img src={AlertTriangleIcon} alt="에러 로고" />
      </S.ErrorLogoWrapper>
      <S.ErrorMessage>{errorMessage}</S.ErrorMessage>
      <S.Container>
        {errorSectionButtonList.map((button) => (
          <Button type="button" key={button.key} styleType={button.buttonType} onClick={button.onClick}>
            <S.ErrorSectionButtonContents>
              <img src={button.imageSrc} alt={button.imageDescription} />
              <span>{button.text}</span>
            </S.ErrorSectionButtonContents>
          </Button>
        ))}
      </S.Container>
    </S.Layout>
  );
};

export default ErrorSection;
