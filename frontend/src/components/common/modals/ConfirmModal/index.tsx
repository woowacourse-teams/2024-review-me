import React from 'react';

import { ButtonStyleType } from '@/types';

import Button from '../../Button';
import FocusTrap from '../../FocusTrap';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface ConfirmModalButton {
  styleType: ButtonStyleType;
  type?: 'button' | 'submit' | 'reset';
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
  const buttonList = [cancelButton, confirmButton];
  return (
    <ModalPortal>
      <ModalBackground closeModal={isClosableOnBackground ? handleClose : null}>
        <FocusTrap>
          <S.ConfirmModalContainer>
            <S.Contents>{children}</S.Contents>
            <S.ButtonContainer>
              {buttonList.map(({ styleType, type, text, handleClick }) => (
                <Button key={text} styleType={styleType} type={type} onClick={handleClick}>
                  {text}
                </Button>
              ))}
            </S.ButtonContainer>
          </S.ConfirmModalContainer>
        </FocusTrap>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ConfirmModal;
