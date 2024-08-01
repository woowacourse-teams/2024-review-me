import { useNavigate } from 'react-router';

import AlertModal from '../AlertModal';

const LoginRedirectModal = () => {
  const navigate = useNavigate();
  const handleClickCloseButton = () => {
    navigate('/home');
  };

  return (
    <AlertModal
      closeButton={{ type: 'primary', handleClick: handleClickCloseButton, content: '로그인 하러가기' }}
      isClosableOnBackground={false}
      handleClose={null}
    >
      <div>
        <p>유효하지 않은 접근이에요.</p>
        <p>로그인인 후 사용해주세요.</p>
      </div>
    </AlertModal>
  );
};

export default LoginRedirectModal;
