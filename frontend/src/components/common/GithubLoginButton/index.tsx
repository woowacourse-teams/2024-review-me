import GithubWhiteLogoIcon from '@/assets/githubWhiteLogo.svg';
import LoginButton from '@/components/common/LoginButton';

interface GithubLoginButtonProps {
  handleGithubLoginButtonClick: () => void;
  $logoStyle?: React.CSSProperties;
  $style?: React.CSSProperties;
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
