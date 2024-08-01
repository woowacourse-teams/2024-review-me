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
  children: React.ReactNode;
}

const ConfirmModal: React.FC<React.PropsWithChildren<ConfirmModalProps>> = ({
  children,
  confirmButton,
  cancelButton,
}) => {
  const buttonList = [confirmButton, cancelButton];
  return (
    <ModalPortal>
      <ModalBackground>
        <S.ConfirmModalInnerWrapper>
          <S.ConfirmModalInner>
            <S.Contents>{children}</S.Contents>
            <S.ButtonContainer>
              {buttonList.map(({ type, text, handleClick }) => (
                <Button key={text} styleType={type} text={text} onClick={handleClick} />
              ))}
            </S.ButtonContainer>
          </S.ConfirmModalInner>
        </S.ConfirmModalInnerWrapper>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ConfirmModal;
