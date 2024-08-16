import { useEffect, useState } from 'react';

import { DataForURL } from '@/apis/group';
import { Button, Input, EyeButton } from '@/components';
import { useEyeButton } from '@/hooks';
import useModals from '@/hooks/useModals';
import { debounce } from '@/utils/debounce';

import usePostDataForURL from '../../queries/usePostDataForURL';
import {
  isValidReviewGroupDataInput,
  isWithinLengthRange,
  isAlphanumeric,
  MAX_PASSWORD_INPUT,
  MAX_VALID_REVIEW_GROUP_DATA_INPUT,
  MIN_PASSWORD_INPUT,
  isValidPasswordInput,
} from '../../utils/validateInput';
import { FormLayout, ReviewURLModal } from '../index';

import * as S from './styles';

// TODO: 디바운스 시간을 모든 경우에 0.3초로 고정할 것인지(전역 상수로 사용) 논의하기
const DEBOUNCE_TIME = 300;

const INVALID_CHAR_ERROR_MESSAGE = `영문(대/소문자) 및 숫자만 입력할 수 있습니다`;
const GROUP_DATA_LENGTH_ERROR_MESSAGE = `최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있습니다.`;
const PASSWORD_LENGTH_ERROR_MESSAGE = `${MIN_PASSWORD_INPUT}자부터 ${MAX_PASSWORD_INPUT}자까지 입력할 수 있습니다.`;

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const URLGeneratorForm = () => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [password, setPassword] = useState('');
  const [reviewURL, setReviewURL] = useState('');

  const [revieweeNameErrorMessage, setRevieweeNameErrorMessage] = useState('');
  const [projectNameErrorMessage, setProjectNameErrorMessage] = useState('');
  const [passwordErrorMessage, setPasswordErrorMessage] = useState('');

  const mutation = usePostDataForURL();
  const { isOff, handleEyeButtonToggle } = useEyeButton();
  const { isOpen, openModal, closeModal } = useModals();

  const isFormValid =
    isValidReviewGroupDataInput(revieweeName) &&
    isValidReviewGroupDataInput(projectName) &&
    isValidPasswordInput(password);

  const postDataForURL = () => {
    const dataForURL: DataForURL = { revieweeName, projectName };

    mutation.mutate(dataForURL, {
      onSuccess: (data) => {
        const completeURL = getCompleteURL(data.reviewRequestCode);
        setReviewURL(completeURL);

        resetInputs();
      },
    });
  };

  const resetInputs = () => {
    setRevieweeName('');
    setProjectName('');
    setPassword('');
  };

  const getCompleteURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/user/review-writing/${reviewRequestCode}`;
  };

  const handleNameInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRevieweeName(event.target.value);
  };

  const handleProjectNameInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setProjectName(event.target.value);
  };

  const handlePasswordInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleUrlCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    postDataForURL();
    openModal(MODAL_KEYS.confirm);
  }, DEBOUNCE_TIME);

  useEffect(() => {
    isWithinLengthRange(revieweeName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setRevieweeNameErrorMessage('')
      : setRevieweeNameErrorMessage(GROUP_DATA_LENGTH_ERROR_MESSAGE);
  }, [revieweeName]);

  useEffect(() => {
    isWithinLengthRange(projectName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setProjectNameErrorMessage('')
      : setProjectNameErrorMessage(GROUP_DATA_LENGTH_ERROR_MESSAGE);
  }, [revieweeName]);

  useEffect(() => {
    // NOTE: URL 요청 버튼 활성화 조건에서는 최소 4자 조건도 체크하지만,
    // 여기서(비밀번호 에러 메세지 설정)는 min 값을 검사하지 않음
    // 현재 onFocus 등의 처리가 없어 항상 에러 메세지가 뜨기 때문
    // 추후 textarea처럼 onfocus, onblur에 대한 훅 사용 예정
    if (!isWithinLengthRange(password, MAX_PASSWORD_INPUT)) {
      setPasswordErrorMessage(PASSWORD_LENGTH_ERROR_MESSAGE);
      return;
    }
    if (!isAlphanumeric(password)) {
      setPasswordErrorMessage(INVALID_CHAR_ERROR_MESSAGE);
      return;
    }

    setPasswordErrorMessage('');
  }, [password]);

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원에게 리뷰를 받아보세요!" direction="column">
        <S.InputContainer>
          <S.Label htmlFor="reviewee-name">본인의 이름을 적어주세요</S.Label>
          <Input
            id="reviewee-name"
            value={revieweeName}
            onChange={handleNameInputChange}
            type="text"
            placeholder="이름"
          />
          <S.ErrorMessage>{revieweeNameErrorMessage}</S.ErrorMessage>
        </S.InputContainer>
        <S.InputContainer>
          <S.Label htmlFor="project-name">함께한 프로젝트 이름을 입력해주세요</S.Label>
          <Input
            id="project-name"
            value={projectName}
            onChange={handleProjectNameInputChange}
            type="text"
            placeholder="review-me"
          />
          <S.ErrorMessage>{projectNameErrorMessage}</S.ErrorMessage>
        </S.InputContainer>
        <S.InputContainer>
          <S.Label htmlFor="password">리뷰 확인에 사용할 비밀번호를 적어주세요</S.Label>
          <S.InputInfo>{`${MIN_PASSWORD_INPUT}~${MAX_PASSWORD_INPUT}자의 영문(대/소문자),숫자만 사용가능해요`}</S.InputInfo>
          <S.PasswordInputContainer>
            <Input
              id="password"
              value={password}
              onChange={handlePasswordInputChange}
              type={isOff ? 'password' : 'text'}
              placeholder="abc123"
              $style={{ border: 'none', outline: 'none' }}
            />
            <EyeButton isOff={isOff} handleEyeButtonToggle={handleEyeButtonToggle} />
          </S.PasswordInputContainer>
          <S.ErrorMessage>{passwordErrorMessage}</S.ErrorMessage>
        </S.InputContainer>
        <Button
          type="button"
          styleType={isFormValid ? 'primary' : 'disabled'}
          onClick={handleUrlCreationButtonClick}
          disabled={!isFormValid}
        >
          리뷰 링크 생성하기
        </Button>
        {isOpen(MODAL_KEYS.confirm) && (
          <ReviewURLModal reviewURL={reviewURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
        )}
      </FormLayout>
    </S.URLGeneratorForm>
  );
};

export default URLGeneratorForm;
