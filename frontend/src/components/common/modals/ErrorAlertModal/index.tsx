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
}

const ErrorAlertModal = ({ closeButton, errorText }: ErrorAlertModalProps) => {
  return (
    <AlertModal closeButton={closeButton}>
      <S.Contents>
        <S.AlertTriangle src={AlertTrianglePrimaryIcon} alt="경고 마크" />
        <S.AlertMessage>{errorText}</S.AlertMessage>
      </S.Contents>
    </AlertModal>
  );
};

export default ErrorAlertModal;
