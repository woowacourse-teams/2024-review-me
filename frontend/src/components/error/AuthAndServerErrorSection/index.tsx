import AlertTriangleIcon from '@/assets/alertTriangle.svg';
import PrimaryHomeIcon from '@/assets/primaryHome.svg';
import PrimaryReloadIcon from '@/assets/primaryReload.svg';
import WhiteHomeIcon from '@/assets/whiteHome.svg';
import WhiteReloadIcon from '@/assets/whiteReload.svg';
import { Button } from '@/components/common';
import { API_ERROR_MESSAGE } from '@/constants';

import { ErrorSectionProps, ErrorSectionButton } from '../ErrorSection';
import * as S from '../ErrorSection/styles';

const AuthAndServerErrorSection = ({ errorMessage, handleReload, handleGoOtherPage }: ErrorSectionProps) => {
  const isServerError = errorMessage === API_ERROR_MESSAGE.serverError;

  const buttonList: ErrorSectionButton[] = [
    {
      buttonType: isServerError ? 'primary' : 'secondary',
      key: 'refreshButton',
      text: '새로고침하기',
      imageSrc: isServerError ? WhiteReloadIcon : PrimaryReloadIcon,
      imageDescription: '새로고침',
      onClick: handleReload,
    },
    {
      buttonType: isServerError ? 'secondary' : 'primary',
      key: 'reviewZoneButton',
      text: '리뷰 연결 페이지',
      imageSrc: isServerError ? PrimaryHomeIcon : WhiteHomeIcon,
      imageDescription: '리뷰 연결 페이지',
      onClick: handleGoOtherPage,
    },
  ];

  const errorSectionButtonList = !isServerError ? buttonList.reverse() : buttonList;

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

export default AuthAndServerErrorSection;
