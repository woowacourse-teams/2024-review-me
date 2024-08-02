import React from 'react';

import { ButtonStyleType } from '@/types';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface ConfirmModalButton {
  type: ButtonStyleType;
  text: string;
  handleClick: (e: React.MouseEvent) => void;
}

interface ConfirmModalProps {
  confirmButton: ConfirmModalButton;
  cancelButton: ConfirmModalButton;
  isClosableOnBackground: boolean;
  handleClose: (() => void) | null;
  children: React.ReactNode;
}

const ConfirmModal: React.FC<React.PropsWithChildren<ConfirmModalProps>> = ({
  children,
  confirmButton,
  cancelButton,
  isClosableOnBackground,
  handleClose,
}) => {
  const buttonList = [confirmButton, cancelButton];
  return (
    <ModalPortal>
      <ModalBackground closeModal={isClosableOnBackground ? handleClose : null}>
        <S.ConfirmModalContainer>
          <S.Contents>{children}</S.Contents>
          <S.ButtonContainer>
            {buttonList.map(({ type, text, handleClick }) => (
              <Button key={text} styleType={type} onClick={handleClick}>
                {text}
              </Button>
            ))}
          </S.ButtonContainer>
        </S.ConfirmModalContainer>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ConfirmModal;
