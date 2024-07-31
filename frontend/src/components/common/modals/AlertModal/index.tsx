import { ButtonType, EssentialPropsWithChildren } from '@/types';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface CloseButton {
  type: ButtonType;
  handleClick: () => void;
}

interface AlertModalProps {
  closeButton: CloseButton;
}

const AlertModal = ({ closeButton, children }: EssentialPropsWithChildren<AlertModalProps>) => {
  return (
    <ModalPortal>
      <ModalBackground closeModal={closeButton.handleClick}>
        <S.AlertModalContainer>
          <S.Contents>{children}</S.Contents>
          <Button
            buttonType={closeButton.type}
            onClick={closeButton.handleClick}
            style={{ width: '100%', minWidth: '30rem', height: '4rem' }}
          >
            닫기
          </Button>
        </S.AlertModalContainer>
      </ModalBackground>
    </ModalPortal>
  );
};

export default AlertModal;
