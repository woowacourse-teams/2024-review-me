import { ButtonStyleType, EssentialPropsWithChildren } from '@/types';

import Button from '../../Button';
import FocusTrap from '../../FocusTrap';
import Portal from '../../Portal';
import ModalBackground from '../ModalBackground';

import * as S from './styles';

interface CloseButton {
  type: ButtonStyleType;
  handleClick: () => void;
  content: React.ReactNode;
}

interface AlertModalProps {
  closeButton: CloseButton;
  isClosableOnBackground: boolean;
  handleClose: (() => void) | null;
}

const AlertModal = ({
  closeButton,
  isClosableOnBackground,
  handleClose,
  children,
}: EssentialPropsWithChildren<AlertModalProps>) => {
  return (
    <Portal>
      <ModalBackground closeModal={isClosableOnBackground ? handleClose : null}>
        <FocusTrap>
          <S.AlertModalContainer>
            <S.Contents>{children}</S.Contents>
            <Button
              styleType={closeButton.type}
              onClick={closeButton.handleClick}
              style={{ width: '100%', minWidth: '30%', height: '4rem' }}
            >
              {closeButton.content}
            </Button>
          </S.AlertModalContainer>
        </FocusTrap>
      </ModalBackground>
    </Portal>
  );
};

export default AlertModal;
