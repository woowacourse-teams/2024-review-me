import { PropsWithChildren } from 'react';

import * as S from './styles';

interface KeywordButtonProps {
  isSelected: boolean;
}

const KeywordButton = ({ isSelected, children }: PropsWithChildren<KeywordButtonProps>) => {
  return <S.KeywordButton isSelected={isSelected}>{children}</S.KeywordButton>;
};

export default KeywordButton;
