import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButton } from '@/components/login';
import { LoginButtonStyleProps } from '@/components/login/LoginButton';

interface GitHubLoginButtonProps extends LoginButtonStyleProps {
  handleClick: () => void;
}

const GitHubLoginButton = ({ handleClick, $logoImgStyle, $buttonStyle }: GitHubLoginButtonProps) => {
  return (
    <LoginButton
      platform="GitHub"
      handleClick={handleClick}
      logoSrc={GithubWhiteLogoIcon}
      $logoImgStyle={$logoImgStyle}
      $buttonStyle={$buttonStyle}
    />
  );
};

export default GitHubLoginButton;
