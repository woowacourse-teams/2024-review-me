import { ButtonType } from '@/types/styles';
import * as S from './styles';

interface ButtonProps {
  buttonType: ButtonType;
  text: string;
}

const Button = ({ buttonType, text }: ButtonProps) => {
  return <S.Button buttonType={buttonType}>{text}</S.Button>;
};

export default Button;
