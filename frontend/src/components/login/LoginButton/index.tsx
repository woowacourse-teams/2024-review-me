import { Button } from '@/components/index';

import * as S from './styles';

interface LoginButtonProps extends LoginButtonStyleProps {
  platform: string;
  logoSrc: string;
  handleClick: () => void;
}

export interface LoginButtonStyleProps {
  $logoStyle?: React.CSSProperties;
  $style?: React.CSSProperties;
}

const LoginButton = ({ platform, logoSrc, handleClick, $logoStyle, $style }: LoginButtonProps) => {
  return (
    <Button onClick={handleClick} styleType="primary" style={$style}>
      <S.ButtonLabelContainer>
        <S.LogoImg src={logoSrc} alt={`${platform} 로고`} $logoStyle={$logoStyle} />
        <span>{platform} 계정으로 로그인하기</span>
      </S.ButtonLabelContainer>
    </Button>
  );
};

export default LoginButton;
