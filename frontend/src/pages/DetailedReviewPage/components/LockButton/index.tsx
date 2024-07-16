import LockIcon from '../../../../assets/Lock.svg';
import UnlockIcon from '../../../../assets/Unlock.svg';

interface LockButtonProps {
  isLock: boolean;
  onClick: () => void;
}

const IMAGE = {
  lock: {
    src: LockIcon,
    alt: 'lock icon',
  },
  unlock: {
    src: UnlockIcon,
    alt: 'unlock icon',
  },
};

const LockButton = ({ isLock, onClick }: LockButtonProps) => {
  const { src, alt } = isLock ? IMAGE.lock : IMAGE.unlock;
  return (
    <button type="button" onClick={onClick}>
      <img src={src} alt={alt} />
    </button>
  );
};

export default LockButton;
