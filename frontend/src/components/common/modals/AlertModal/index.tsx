import { ButtonStyleType, EssentialPropsWithChildren } from '@/types';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface CloseButton {
  type: ButtonStyleType;
  handleClick: () => void;
  content: React.ReactNode;
}

interface AlertModalProps {
  closeButton: CloseButton;
  isCloseOnBackground?: boolean;
  isCloseOnEsc?: boolean;
  handleClose?: () => void;
}

const AlertModal = ({
  closeButton,
  isCloseOnBackground = true,
  isCloseOnEsc = true,
  handleClose = closeButton.handleClick,
  children,
}: EssentialPropsWithChildren<AlertModalProps>) => {
  return (
    <ModalPortal>
      <ModalBackground
        closeModalOnBackground={isCloseOnBackground ? handleClose : () => {}}
        closeModalOnEsc={isCloseOnEsc ? handleClose : () => {}}
      >
        <S.AlertModalContainer>
          <S.Contents>{children}</S.Contents>
          <Button
            styleType={closeButton.type}
            onClick={closeButton.handleClick}
            style={{ width: '100%', minWidth: '30rem', height: '4rem' }}
          >
            {closeButton.content}
          </Button>
        </S.AlertModalContainer>
      </ModalBackground>
    </ModalPortal>
  );
};

export default AlertModal;
