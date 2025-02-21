import * as S from './styles';

interface LoginToggleButtonProps {
  goToLogin: boolean;
  handleClick: () => void;
}

const LoginToggleButton = ({ goToLogin, handleClick }: LoginToggleButtonProps) => {
  return (
    <S.LoginToggleButton onClick={handleClick}>
      {goToLogin ? '회원으로 로그인하기' : '로그인 없이 리뷰 받아보기'}
    </S.LoginToggleButton>
  );
};

export default LoginToggleButton;
