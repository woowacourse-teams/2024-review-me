import { useState } from 'react';
import { useNavigate } from 'react-router';

import { Input, Button, EyeButton } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';
import { ROUTE } from '@/constants/route';
import { useCheckPasswordValidation, useEyeButton } from '@/hooks';

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

  const handleValidatedPassword = () => {
    setErrorMessage('');
    navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
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
    <ContentModal
      title={REVIEW_PASSWORD_INPUT_MESSAGE}
      handleClose={closeModal}
      isClosableOnBackground={false}
      // NOTE: ContentModal의 미디어 쿼리로 padding이 줄어드는 것을 방어하기 위한 인라인 스타일 적용
      $style={{ padding: '3.2rem' }}
    >
      <S.PasswordModal>
        <S.InputContainer>
          <S.PasswordInputContainer>
            <Input
              id="password"
              value={password}
              onChange={handlePasswordInputChange}
              type={isOff ? 'password' : 'text'}
              $style={{ width: '100%' }}
            />
            <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
          </S.PasswordInputContainer>
          <Button styleType="primary" type="submit" onClick={handleConfirmButtonClick}>
            확인
          </Button>
        </S.InputContainer>
        <S.ErrorMessageWrapper>{errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}</S.ErrorMessageWrapper>
      </S.PasswordModal>
    </ContentModal>
  );
};

export default PasswordModal;
