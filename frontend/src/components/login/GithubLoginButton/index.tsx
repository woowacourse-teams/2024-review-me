import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import { LoginButton } from '@/components/login';
import { LoginButtonStyleProps } from '@/components/login/LoginButton';

interface GithubLoginButtonProps extends LoginButtonStyleProps {
  handleClick: () => void;
}

const GithubLoginButton = ({ handleClick, $logoImgStyle, $buttonStyle }: GithubLoginButtonProps) => {
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

export default GithubLoginButton;
