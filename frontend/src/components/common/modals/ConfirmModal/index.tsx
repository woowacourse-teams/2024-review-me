import React, { PropsWithChildren } from 'react';

import { ButtonType } from '@/types/styles';

import Button from '../../Button';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface ConfirmModalButton {
  type: ButtonType;
  text: string;
  handleClick: (e: React.MouseEvent) => void;
}

interface ConfirmModalProps {
  confirmButton: ConfirmModalButton;
  cancelButton: ConfirmModalButton;
}

const ConfirmModal: React.FC<PropsWithChildren<ConfirmModalProps>> = ({ children, confirmButton, cancelButton }) => {
  const buttonList = [confirmButton, cancelButton];
  return (
    <ModalPortal>
      <ModalBackground>
        <S.ConfirmModalInnerWrapper>
          <S.ConfirmModalInner>
            <S.Contents>{children}</S.Contents>
            <S.ButtonContainer>
              {buttonList.map(({ type, text, handleClick }) => (
                <Button key={text} buttonType={type} text={text} onClick={handleClick} />
              ))}
            </S.ButtonContainer>
          </S.ConfirmModalInner>
        </S.ConfirmModalInnerWrapper>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ConfirmModal;
