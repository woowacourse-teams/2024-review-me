import LockIcon from '@/assets/lock.svg';
import UnlockIcon from '@/assets/unLock.svg';

import * as S from './styles';

const IMAGE = {
  lock: {
    src: LockIcon,
    alt: 'lock icon',
    text: '비공개',
  },
  unlock: {
    src: UnlockIcon,
    alt: 'unlock icon',
    text: '공개',
  },
};
interface ToggleButtonProps {
  name: keyof typeof IMAGE;
  $isPublic: boolean;
  handleClickToggleButton: () => void;
}

const ToggleButton = ({ name, $isPublic, handleClickToggleButton }: ToggleButtonProps) => {
  const { src, alt, text } = IMAGE[name];
  const $isActive = name === 'lock' ? $isPublic : !$isPublic;

  return (
    <S.Button type="button" $isActive={$isActive} name={name} onClick={handleClickToggleButton}>
      <img src={src} alt={alt} />
      <span>{text}</span>
    </S.Button>
  );
};

interface LockToggleProps extends Omit<ToggleButtonProps, 'name'> {}

const LockToggle = (props: LockToggleProps) => {
  return (
    <S.LockToggle>
      <ToggleButton name="lock" {...props} />
      <ToggleButton name="unlock" {...props} />
    </S.LockToggle>
  );
};

export default LockToggle;
