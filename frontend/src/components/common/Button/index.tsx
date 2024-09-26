import { ButtonStyleType, EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  styleType: ButtonStyleType;
}

const Button = ({
  styleType: buttonType,
  type,
  style,
  children,
  onClick,
  ...rest
}: EssentialPropsWithChildren<ButtonProps>) => {
  return (
    <S.Button buttonType={buttonType} type={type} onClick={onClick} $style={style} {...rest}>
      {children}
    </S.Button>
  );
};

export default Button;
