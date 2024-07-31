import { ButtonType, EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  buttonType: ButtonType;
}

const Button = ({ buttonType, style, children, onClick }: EssentialPropsWithChildren<ButtonProps>) => {
  return (
    <S.Button buttonType={buttonType} onClick={onClick} $style={style}>
      {children}
    </S.Button>
  );
};

export default Button;
