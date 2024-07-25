import { ButtonHTMLAttributes } from 'react';

import * as S from './styles';

interface KeywordButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  isSelected: boolean;
}

const KeywordButton = ({ isSelected, children, ...props }: React.PropsWithChildren<KeywordButtonProps>) => {
  return (
    <S.KeywordButton isSelected={isSelected} type="button" {...props}>
      {children}
    </S.KeywordButton>
  );
};

export default KeywordButton;
