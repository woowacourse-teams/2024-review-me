import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButtonStyleProps } from '@/components/common/LoginButton';
import { LoginButton } from '@/components/index';

interface GithubLoginButtonProps extends LoginButtonStyleProps {
  handleClick: () => void;
}

const GithubLoginButton = ({ handleClick, $logoStyle, $style }: GithubLoginButtonProps) => {
  return (
    <LoginButton
      platform="깃허브"
      engPlatform="github"
      handleClick={handleClick}
      logoSrc={GithubWhiteLogoIcon}
      $logoStyle={$logoStyle}
      $style={$style}
    ></LoginButton>
  );
};

export default GithubLoginButton;
