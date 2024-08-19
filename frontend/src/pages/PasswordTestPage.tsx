import { useRef, useState } from 'react';
import { useNavigate } from 'react-router';

import { ROUTES } from '@/constants/routes';
import { useCheckPasswordValidation, useGroupAccessCode, useSearchParamAndQuery } from '@/hooks';

const PasswordTestPage = () => {
  // NOTE: 연결 페이지가 develop에 머지되지 않아서, 비밀 번호 확인을 위한 컴포넌트를 생성 추후에 삭제 예정

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  if (!reviewRequestCode) throw new Error('유효하지 않은 리뷰 요청 코드입니다.');
  const inputRef = useRef<HTMLInputElement>(null);
  const [password, setPassword] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState<string>('');
  const navigate = useNavigate();
  const { updateGroupAccessCode } = useGroupAccessCode();

  const handleValidatedPassword = () => {
    updateGroupAccessCode(password);
    setErrorMessage('');
    navigate(`/${ROUTES.reviewList}`);
  };

  const handleInvalidatedPassword = (error: Error) => {
    setErrorMessage(error.message);
  };

  useCheckPasswordValidation({
    groupAccessCode: password,
    reviewRequestCode,
    onSuccess: handleValidatedPassword,
    onError: handleInvalidatedPassword,
  });

  const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    if (!inputRef.current) return;

    setPassword(inputRef.current.value);
  };
  return (
    <form>
      <input type="password" ref={inputRef} />
      <button type="submit" onClick={handleSubmit}>
        확인
      </button>
      <div>{errorMessage}</div>
    </form>
  );
};

export default PasswordTestPage;
