import { useNavigate } from 'react-router';

import AlertModal from '../AlertModal';

const LoginRedirectModal = () => {
  const navigate = useNavigate();
  const handleClickCloseButton = () => {
    navigate('/');
  };

  return (
    <AlertModal
      closeButton={{ type: 'primary', handleClick: handleClickCloseButton, content: '홈페이지로 이동' }}
      isClosableOnBackground={false}
      handleClose={null}
    >
      <div>
        <p>유효하지 않은 접근이에요</p>
        <p>생성한 리뷰 링크를 통해 접속해주세요</p>
      </div>
    </AlertModal>
  );
};

export default LoginRedirectModal;
