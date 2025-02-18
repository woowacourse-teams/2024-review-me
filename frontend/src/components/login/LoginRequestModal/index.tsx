import { useTheme } from '@emotion/react';

import { ContentModal, GitHubLoginButton } from '@/components';

import { ActionOnLogin } from '../GitHubLoginButton';

import * as S from './styles';

const LOGIN_REQUEST_TITLE = {
  loginIntent: '로그인하시겠어요?',
  membershipCheck: '회원이신가요?',
} as const;

type LoginRequestTitle = keyof typeof LOGIN_REQUEST_TITLE;

interface LoginRequestModalProps {
  action: ActionOnLogin;
  titleType: LoginRequestTitle;
  closeModal: () => void;
}

const LoginRequestModal = ({ action, titleType, closeModal }: LoginRequestModalProps) => {
  const theme = useTheme();

  return (
    <ContentModal
      title={LOGIN_REQUEST_TITLE[titleType]}
      handleClose={closeModal}
      isClosableOnBackground={true}
      $style={{
        paddingBottom: '0.8rem',
      }}
    >
      <S.LoginRequestModal>
        <S.LoginRequestLabel>로그인 후 간편하게 받은 리뷰를 확인하세요!</S.LoginRequestLabel>
        <GitHubLoginButton
          action={action}
          $logoImgStyle={{ height: '3rem' }}
          $buttonStyle={{ fontSize: theme.fontSize.small, height: '4rem', width: '100%' }}
        />
      </S.LoginRequestModal>
    </ContentModal>
  );
};

export default LoginRequestModal;
