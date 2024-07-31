import AlertTrianglePrimaryIcon from '@/assets/alertTrianglePrimary.svg';
import { ButtonType } from '@/types';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface CloseButton {
  type: ButtonType;
  handleClick: () => void;
}

interface ErrorAlertModalProps {
  closeButton: CloseButton;
  text: string;
}

const ErrorAlertModal = ({ closeButton, text }: ErrorAlertModalProps) => {
  return (
    <ModalPortal>
      <ModalBackground closeModal={closeButton.handleClick}>
        <S.ErrorAlertModalContainer>
          <S.Contents>
            <S.AlertTriangle src={AlertTrianglePrimaryIcon} alt="경고 마크" />
            <S.AlertMessage>{text}</S.AlertMessage>
          </S.Contents>
          <Button
            buttonType={closeButton.type}
            onClick={closeButton.handleClick}
            style={{ width: '100%', minWidth: '30rem', height: '4rem' }}
          >
            닫기
          </Button>
        </S.ErrorAlertModalContainer>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ErrorAlertModal;
