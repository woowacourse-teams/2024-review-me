import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButtonStyleProps } from '@/components/common/LoginButton';
import { LoginButton } from '@/components/index';

interface GithubLoginButtonProps extends LoginButtonStyleProps {
  handleGithubLoginButtonClick: () => void;
}

const GithubLoginButton = ({ handleGithubLoginButtonClick, $logoStyle, $style }: GithubLoginButtonProps) => {
  return (
    <LoginButton
      platform="깃허브"
      engPlatform="github"
      handleLoginButtonClick={handleGithubLoginButtonClick}
      logoSrc={GithubWhiteLogoIcon}
      $logoStyle={$logoStyle}
      $style={$style}
    ></LoginButton>
  );
};

export default GithubLoginButton;
