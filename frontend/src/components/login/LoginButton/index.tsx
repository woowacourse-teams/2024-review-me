import { Button } from '@/components/index';

import * as S from './styles';

interface LoginButtonProps extends LoginButtonStyleProps {
  platform: string;
  logoSrc: string;
  handleClick: () => void;
}

export interface LoginButtonStyleProps {
  $logoImgStyle?: React.CSSProperties;
  $buttonStyle?: React.CSSProperties;
}

const LoginButton = ({ platform, logoSrc, handleClick, $logoImgStyle, $buttonStyle }: LoginButtonProps) => {
  return (
    <Button onClick={handleClick} styleType="primary" style={$buttonStyle}>
      <S.ButtonLabelContainer>
        <S.LogoImg src={logoSrc} alt={`${platform} 로고`} $logoImgStyle={$logoImgStyle} />
        <span>{platform} 계정으로 로그인하기</span>
      </S.ButtonLabelContainer>
    </Button>
  );
};

export default LoginButton;
