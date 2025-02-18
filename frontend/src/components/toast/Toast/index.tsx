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
  durationMS?: number;
  position?: ToastPositionType;
}

const toastIconMap: Record<ToastType, string> = {
  success: CheckedCircleIcon,
  error: WarningIcon,
  confirm: AlertIcon,
};

const Toast = ({ type = 'success', message, durationMS = 5000, position = 'bottom' }: ToastProps) => {
  return (
    <Portal disableScroll={false}>
      <S.ToastContainer $animationDurationMS={durationMS} $position={position}>
        <S.ToastIcon src={toastIconMap[type]} alt={type} />
        <S.ToastMessage>{message}</S.ToastMessage>
      </S.ToastContainer>
    </Portal>
  );
};

export default Toast;
