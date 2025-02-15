import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButton } from '@/components/login';
import { LoginButtonStyleProps } from '@/components/login/LoginButton';
import { GITHUB_AUTHORIZATION_URL } from '@/constants';

interface GitHubLoginButtonProps extends LoginButtonStyleProps {
  // handleClick: () => void;
}

const GitHubLoginButton = ({ $logoImgStyle, $buttonStyle }: GitHubLoginButtonProps) => {
  const redirectToGitHub = () => {
    window.location.href = GITHUB_AUTHORIZATION_URL;
  };

  return (
    <LoginButton
      platform="GitHub"
      handleClick={redirectToGitHub}
      logoSrc={GithubWhiteLogoIcon}
      $logoImgStyle={$logoImgStyle}
      $buttonStyle={$buttonStyle}
    />
  );
};

export default GitHubLoginButton;
