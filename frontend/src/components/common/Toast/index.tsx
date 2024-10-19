import { useEffect } from 'react';

import * as S from './styles';

export type ToastPositionType = 'top' | 'bottom';

interface IconProps {
  src: string;
  alt: string;
}

interface ToastModalProps {
  icon?: IconProps;
  message: string;
  duration: number;
  position: ToastPositionType;
  handleOpenModal: (isOpen: boolean) => void;
}

const Toast = ({ icon, message, duration, position, handleOpenModal }: ToastModalProps) => {
  useEffect(() => {
    const timer = setTimeout(() => {
      handleOpenModal(false);
    }, duration * 1000);

    return () => clearTimeout(timer);
  }, [duration, handleOpenModal]);

  return (
    <S.ToastModalContainer duration={duration} position={position}>
      {icon && <S.WarningIcon src={icon.src} alt={icon.alt} />}
      <S.ErrorMessage>{message}</S.ErrorMessage>
    </S.ToastModalContainer>
  );
};

export default Toast;
