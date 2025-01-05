import React from 'react';
import { NavigateOptions, useNavigate } from 'react-router';

import BackButtonIcon from '@/assets/backButton.svg';

import * as S from './styles';

interface BackButtonProps {
  prevPath: string;
  navigateOptions?: NavigateOptions;
  buttonStyle?: React.CSSProperties;
  wrapperStyle?: React.CSSProperties;
}

const BackButton = ({ prevPath, navigateOptions, buttonStyle, wrapperStyle }: BackButtonProps) => {
  const navigate = useNavigate();

  const handleBackButtonClick = () => {
    navigate(prevPath, navigateOptions);
  };

  return (
    <S.BackButtonWrapper $style={wrapperStyle}>
      <S.BackButton onClick={handleBackButtonClick} $style={buttonStyle} type="button">
        <img src={BackButtonIcon} alt="뒤로가기 버튼" />
      </S.BackButton>
    </S.BackButtonWrapper>
  );
};

export default BackButton;
