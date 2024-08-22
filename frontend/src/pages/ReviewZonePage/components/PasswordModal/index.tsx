import { useState } from 'react';
import { useNavigate } from 'react-router';

import { Input, Button, EyeButton } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';
import { ROUTE } from '@/constants/route';
import { useCheckPasswordValidation, useEyeButton, useGroupAccessCode } from '@/hooks';

import * as S from './styles';

const REVIEW_PASSWORD_INPUT_MESSAGE = '리뷰 확인을 위해 설정한 비밀번호를 입력해주세요';

interface PasswordModalProps {
  closeModal: () => void;
  reviewRequestCode: string;
}

const PasswordModal = ({ closeModal, reviewRequestCode }: PasswordModalProps) => {
  const [password, setPassword] = useState('');
  const [validatedPassword, setValidatedPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();

  const { isOff, handleEyeButtonToggle } = useEyeButton();

  const { updateGroupAccessCode } = useGroupAccessCode();

  const handleValidatedPassword = () => {
    updateGroupAccessCode(password);
    setErrorMessage('');
    navigate(`/${ROUTE.reviewList}`);
  };

  const handleInvalidatedPassword = (error: Error) => {
    setErrorMessage(error.message);
  };

  useCheckPasswordValidation({
    groupAccessCode: validatedPassword,
    reviewRequestCode,
    onSuccess: handleValidatedPassword,
    onError: handleInvalidatedPassword,
  });

  const handleConfirmButtonClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    if (!password) return;

    setValidatedPassword(password);
  };

  const handlePasswordInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <ContentModal title={REVIEW_PASSWORD_INPUT_MESSAGE} handleClose={closeModal}>
      <S.PasswordModal>
        <S.InputContainer>
          <S.PasswordInputContainer>
            <Input
              id="password"
              value={password}
              onChange={handlePasswordInputChange}
              type={isOff ? 'password' : 'text'}
              $style={{ width: '100%', paddingRight: '3rem' }}
            />
            <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
          </S.PasswordInputContainer>
          <Button styleType="primary" type="submit" onClick={handleConfirmButtonClick}>
            확인
          </Button>
        </S.InputContainer>
        {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
      </S.PasswordModal>
    </ContentModal>
  );
};

export default PasswordModal;
