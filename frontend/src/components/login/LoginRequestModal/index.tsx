import { useTheme } from '@emotion/react';

import { ContentModal, GithubLoginButton } from '@/components';

import * as S from './styles';

type LoginRequestTitle = 'loginIntent' | 'membershipCheck';

interface LoginRequestModalProps {
  titleType: LoginRequestTitle;
  closeModal: () => void;
}

const LoginRequestModal = ({ titleType, closeModal }: LoginRequestModalProps) => {
  const theme = useTheme();

  const getTitleLabel = (titleType: LoginRequestTitle) => {
    if (titleType === 'loginIntent') return '로그인하시겠어요?';
    if (titleType === 'membershipCheck') return '회원이신가요?';
  };

  return (
    <ContentModal title={getTitleLabel(titleType)} handleClose={closeModal} isClosableOnBackground={true} $style={{}}>
      <S.LoginRequestModal>
        <S.LoginRequestLabel>로그인 후 간편하게 받은 리뷰를 확인하세요!</S.LoginRequestLabel>
        <GithubLoginButton
          handleClick={() => {}}
          $logoStyle={{ height: '3rem' }}
          $style={{ fontSize: theme.fontSize.small, height: '4rem', width: '100%' }}
        />
      </S.LoginRequestModal>
    </ContentModal>
  );
};

export default LoginRequestModal;
