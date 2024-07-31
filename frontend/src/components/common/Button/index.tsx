import { ButtonType } from '@/types';

import * as S from './styles';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  buttonType: ButtonType;
  text: string;
}

const Button = ({ buttonType, text }: ButtonProps) => {
  return <S.Button buttonType={buttonType}>{text}</S.Button>;
};

export default Button;
