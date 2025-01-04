import { useTheme } from '@emotion/react';
import { useState } from 'react';

import { ContentModal, GithubLoginButton } from '@/components';

import * as S from './styles';

type LoginRequestTitle = 'loginIntent' | 'membershipCheck';

interface LoginRequestModalProps {
  titleType: LoginRequestTitle;
  closeModal: () => void;
}

const LoginRequestModal = ({ titleType, closeModal }: LoginRequestModalProps) => {
  const [errorMessage, setErrorMessage] = useState('');
  const theme = useTheme();

  const getTitleLabel = (titleType: LoginRequestTitle) => {
    if (titleType === 'loginIntent') return '로그인하시겠어요?';
    if (titleType === 'membershipCheck') return '회원이신가요?';
  };

  // 에러 메세지 확인용. 추후 API 호출로 변경
  const handleClickLoginButton = () => {
    setErrorMessage('에러 메세지');
  };

  return (
    <ContentModal
      title={getTitleLabel(titleType)}
      handleClose={closeModal}
      isClosableOnBackground={true}
      $style={{
        paddingBottom: '0.8rem',
      }}
    >
      <S.LoginRequestModal>
        <S.LoginRequestLabel>로그인 후 간편하게 받은 리뷰를 확인하세요!</S.LoginRequestLabel>
        <GithubLoginButton
          handleClick={handleClickLoginButton}
          $logoStyle={{ height: '3rem' }}
          $style={{ fontSize: theme.fontSize.small, height: '4rem', width: '100%' }}
        />
        {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
      </S.LoginRequestModal>
    </ContentModal>
  );
};

export default LoginRequestModal;
