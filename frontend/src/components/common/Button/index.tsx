import { ButtonType } from '@/types/styles';

import * as S from './styles';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  buttonType: ButtonType;
  text: string;
  icon?: string;
}

const Button = ({ buttonType, text, icon }: ButtonProps) => {
  return (
    <S.Button buttonType={buttonType}>
      {icon && <S.Icon src={icon} alt="아이콘" />}
      {text}
    </S.Button>
  );
};

export default Button;
