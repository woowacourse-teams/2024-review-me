import AlertTrianglePrimaryIcon from '@/assets/alertTrianglePrimary.svg';
import { ButtonStyleType } from '@/types';

import AlertModal from '../AlertModal';

import * as S from './styles';

interface CloseButton {
  type: ButtonStyleType;
  handleClick: () => void;
  content: React.ReactNode;
}

interface ErrorAlertModalProps {
  closeButton: CloseButton;
  errorText: string;
  handleClose: () => void;
}

const ErrorAlertModal = ({ closeButton, errorText, handleClose }: ErrorAlertModalProps) => {
  return (
    <AlertModal closeButton={closeButton} isClosableOnBackground={true} handleClose={handleClose}>
      <S.Contents>
        <S.AlertTriangle src={AlertTrianglePrimaryIcon} alt="경고 마크" />
        <p>{errorText}</p>
      </S.Contents>
    </AlertModal>
  );
};

export default ErrorAlertModal;
