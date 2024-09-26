import AlertTrianglePrimaryIcon from '@/assets/alertTrianglePrimary.svg';
import { ButtonStyleType, EssentialPropsWithChildren } from '@/types';

import AlertModal from '../AlertModal';

import * as S from './styles';

export interface ErrorAlertModalCloseButton {
  type: ButtonStyleType;
  handleClick: () => void;
  content: React.ReactNode;
}

interface ErrorAlertModalProps {
  closeButton: ErrorAlertModalCloseButton;
  handleClose: () => void;
}

const ErrorAlertModal = ({ closeButton, children, handleClose }: EssentialPropsWithChildren<ErrorAlertModalProps>) => {
  return (
    <AlertModal closeButton={closeButton} isClosableOnBackground={true} handleClose={handleClose}>
      <S.Contents>
        <S.AlertTriangle src={AlertTrianglePrimaryIcon} alt="경고 마크" />
        {children}
      </S.Contents>
    </AlertModal>
  );
};

export default ErrorAlertModal;
