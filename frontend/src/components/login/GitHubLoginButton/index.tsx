import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButton } from '@/components/login';
import { LoginButtonStyleProps } from '@/components/login/LoginButton';
import { GITHUB_AUTHORIZATION_URL } from '@/constants';

// 로그인 시 동작.
export type ActionOnLogin = 'none' | 'reviewWrite' | 'reviewCheck';
export type LoginActionContextType = {
  prevUrl: string;
  action: ActionOnLogin;
};

interface GitHubLoginButtonProps extends LoginButtonStyleProps {
  action: ActionOnLogin;
}

const GitHubLoginButton = ({ action, $logoImgStyle, $buttonStyle }: GitHubLoginButtonProps) => {
  const currentUrl = location.pathname;
  const state = JSON.stringify({ prevUrl: currentUrl, action: action });
  const redirectToGitHub = () => {
    window.location.href = `${GITHUB_AUTHORIZATION_URL}&state=${encodeURIComponent(state)}`;
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
