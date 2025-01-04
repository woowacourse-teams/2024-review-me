import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButton } from '@/components/login';
import { LoginButtonStyleProps } from '@/components/login/LoginButton';

interface GithubLoginButtonProps extends LoginButtonStyleProps {
  handleClick: () => void;
}

const GithubLoginButton = ({ handleClick, $logoStyle, $style }: GithubLoginButtonProps) => {
  return (
    <LoginButton
      platform="GitHub"
      handleClick={handleClick}
      logoSrc={GithubWhiteLogoIcon}
      $logoStyle={$logoStyle}
      $style={$style}
    />
  );
};

export default GithubLoginButton;
