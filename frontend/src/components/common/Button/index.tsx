import { ButtonType } from '@/types/styles';

import * as S from './styles';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  buttonType: ButtonType;
  text: string;
  image?: string;
  imageDescription?: string;
}

const Button = ({ buttonType, text, image, imageDescription, onClick }: ButtonProps) => {
  return (
    <S.Button buttonType={buttonType} onClick={onClick}>
      {image && <S.Image src={image} alt={imageDescription || ''} />}
      {text}
    </S.Button>
  );
};

export default Button;
