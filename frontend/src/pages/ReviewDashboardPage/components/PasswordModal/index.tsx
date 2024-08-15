import { useState } from 'react';

import { Input, Button } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';

import * as S from './styles';
interface PasswordModalProps {
  closeModal: () => void;
}

const PasswordModal = ({ closeModal }: PasswordModalProps) => {
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleConfirmButtonClick = () => {
    console.log('비밀번호 제출');
    //closeModal();
  };

  const handlePasswordInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <ContentModal handleClose={closeModal}>
      <S.PasswordModal>
        <S.ModalTitle>리뷰 확인을 위해 비밀 번호를 입력해주세요</S.ModalTitle>
        <S.Label htmlFor="password">비밀번호</S.Label>
        <S.InputContainer>
          <Input
            id="password"
            value={password}
            onChange={handlePasswordInputChange}
            type="password"
            placeholder="abc123"
            $style={{ flex: '8' }}
          />
          <Button styleType="primary" type="button" onClick={handleConfirmButtonClick}>
            확인
          </Button>
        </S.InputContainer>
        {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
      </S.PasswordModal>
    </ContentModal>
  );
};

export default PasswordModal;
