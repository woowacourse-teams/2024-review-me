import { PropsWithChildren } from 'react';

import * as S from './styles';

interface KeywordButtonProps {
  isSelected: boolean;
  onClick: () => void;
}

const KeywordButton = ({ isSelected, children, onClick }: PropsWithChildren<KeywordButtonProps>) => {
  return (
    <S.KeywordButton
      isSelected={isSelected}
      onClick={(e) => {
        e.preventDefault();
        onClick();
      }}
    >
      {children}
    </S.KeywordButton>
  );
};

export default KeywordButton;
