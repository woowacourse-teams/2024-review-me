import EyeIcon from '@/assets/eye.svg';
import EyeOffIcon from '@/assets/eyeOff.svg';

import * as S from './styles';

interface EyeButtonProps {
  isOff: boolean;
  handleEyeButtonToggle: () => void;
}

const EyeButton = ({ isOff, handleEyeButtonToggle }: EyeButtonProps) => {
  return (
    <S.EyeButton onClick={handleEyeButtonToggle} type="button">
      <img src={isOff ? EyeOffIcon : EyeIcon} alt="비밀번호 공개 버튼" />
    </S.EyeButton>
  );
};

export default EyeButton;
