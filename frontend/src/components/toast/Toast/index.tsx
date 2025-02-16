import AlertIcon from '@/assets/alertTriangle.svg';
import CheckedCircleIcon from '@/assets/checkedCircle.svg';
import WarningIcon from '@/assets/warning.svg';
import { Portal } from '@/components/common';

import * as S from './styles';

export type ToastType = 'success' | 'error' | 'confirm';
export type ToastPositionType = 'top' | 'bottom';

export interface ToastProps {
  type?: ToastType;
  message: string;
  duration?: number;
  position?: ToastPositionType;
}

const getToastIcon = (type: ToastType) => {
  if (type === 'success') return CheckedCircleIcon;
  if (type === 'error') return WarningIcon;
  if (type === 'confirm') return AlertIcon;
  return null;
};

const Toast = ({ type = 'success', message, duration = 5, position = 'bottom' }: ToastProps) => {
  return (
    <Portal disableScroll={false}>
      <S.ToastContainer duration={duration * 100000} position={position}>
        <S.ToastIcon src={getToastIcon(type!)} alt={type} />
        <S.ToastMessage>{message}</S.ToastMessage>
      </S.ToastContainer>
    </Portal>
  );
};

export default Toast;
