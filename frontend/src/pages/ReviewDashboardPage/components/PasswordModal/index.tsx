import { useState } from 'react';
import { useNavigate } from 'react-router';

import { Input, Button } from '@/components';
// import { Input, Button, EyeButton } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';
import { ROUTES } from '@/constants/routes';
// import { useEyeButton } from '@/hooks';

import * as S from './styles';
interface PasswordModalProps {
  closeModal: () => void;
}

// NOTE : EyeButton이 있는 PR merge시 pull 받아온 뒤 주석 해제 코드 사용

const PasswordModal = ({ closeModal }: PasswordModalProps) => {
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();
  //const { isOff, handleEyeButtonToggle } = useEyeButton();

  const handleConfirmButtonClick = () => {
    console.log('비밀번호 제출');

    // NOTE: 추후 이곳에 API 호출 함수 추가

    //closeModal();
    // navigate(`/${ROUTES.reviewWriting}/5`); // NOTE: 유효하지 않은 경로
  };

  const handlePasswordInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <ContentModal handleClose={closeModal}>
      <S.PasswordModal>
        <S.ModalTitle>리뷰 확인을 위해 설정한 비밀 번호를 입력해주세요</S.ModalTitle>
        {/* <S.InputContainer>
          <S.PasswordInputContainer>
            <Input
              id="password"
              value={password}
              onChange={handlePasswordInputChange}
              type={isOff ? 'password' : 'text'}
              placeholder="abc123"
              $style={{ flex: '8' }}
            />
            <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
          </S.PasswordInputContainer>
          <Button styleType="primary" type="button" onClick={handleConfirmButtonClick}>
            확인
          </Button>
        </S.InputContainer> */}
        {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
      </S.PasswordModal>
    </ContentModal>
  );
};

export default PasswordModal;
