import React from 'react';
import { NavigateOptions, useNavigate } from 'react-router';

import BackButtonIcon from '@/assets/backButton.svg';

import * as S from './styles';

interface BackButtonProps {
  prevPath: string;
  options?: NavigateOptions;
  style?: React.CSSProperties;
}

const BackButton = ({ prevPath, options, style }: BackButtonProps) => {
  const navigate = useNavigate();

  const handleBackButtonClick = () => {
    navigate(prevPath, options);
  };

  return (
    <S.BackButton onClick={handleBackButtonClick} $style={style}>
      <S.BackButtonImage src={BackButtonIcon} alt="뒤로가기 버튼" />
    </S.BackButton>
  );
};

export default BackButton;
