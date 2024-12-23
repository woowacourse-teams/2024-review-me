import Button from '@/components/common/Button';
import { calculateParticle } from '@/utils';

import * as S from './styles';

export interface LoginButtonProps {
  platform: string;
  engPlatform?: string;
  logoSrc: string;
  handleLoginButtonClick: () => void;
  $logoStyle?: React.CSSProperties;
  $style?: React.CSSProperties;
}
const LoginButton = ({
  platform,
  engPlatform,
  logoSrc,
  handleLoginButtonClick,
  $logoStyle,
  $style,
}: LoginButtonProps) => {
  return (
    <Button onClick={handleLoginButtonClick} styleType="primary" style={$style}>
      <S.ButtonLabelContainer>
        <S.LogoImg src={logoSrc} alt={`${platform} 로고`} $logoStyle={$logoStyle} />
        <span>
          {engPlatform || platform}
          {calculateParticle({
            target: platform,
            particles: { withFinalConsonant: '으로', withoutFinalConsonant: '로' },
          })}{' '}
          로그인하기
        </span>
      </S.ButtonLabelContainer>
    </Button>
  );
};

export default LoginButton;
