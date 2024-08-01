import { ButtonStyleType, EssentialPropsWithChildren } from '@/types';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface CloseButton {
  type: ButtonStyleType;
  handleClick: () => void;
}

interface AlertModalProps {
  closeButton: CloseButton;
}

const AlertModal = ({ closeButton, children }: EssentialPropsWithChildren<AlertModalProps>) => {
  return (
    <ModalPortal>
      <ModalBackground closeModal={() => {}}>
        <S.AlertModalContainer>
          <S.Contents>{children}</S.Contents>
          <Button
            styleType={closeButton.type}
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
