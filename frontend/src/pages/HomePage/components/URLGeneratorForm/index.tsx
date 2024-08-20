import { useEffect, useState } from 'react';

import { DataForReviewRequestCode } from '@/apis/group';
import { Button, Input, EyeButton } from '@/components';
import { ROUTE } from '@/constants/route';
import { useEyeButton } from '@/hooks';
import useModals from '@/hooks/useModals';
import { debounce } from '@/utils/debounce';

import usePostDataForReviewRequestCode from '../../queries/usePostDataForReviewRequestCode';
import {
  isNotEmptyInput,
  isValidReviewGroupDataInput,
  isWithinLengthRange,
  MAX_PASSWORD_INPUT,
  MAX_VALID_REVIEW_GROUP_DATA_INPUT,
  MIN_PASSWORD_INPUT,
} from '../../utils/validateInput';
import { FormLayout, ReviewZoneURLModal } from '../index';

import { usePasswordValidation } from './../../../../hooks/usePasswordValidation';
import * as S from './styles';

// TODO: 디바운스 시간을 모든 경우에 0.3초로 고정할 것인지(전역 상수로 사용) 논의하기
const DEBOUNCE_TIME = 300;

const GROUP_DATA_LENGTH_ERROR_MESSAGE = `최대 ${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있어요`;

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const URLGeneratorForm = () => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  // NOTE: 이 password는 groupAccessCode로 사용됩니다.
  // groupAccessCode로 통일하기로 했지만 이미 이 페이지에서는 pwd로 작업한 게 많아서 놔두고
  // API 요청 함수와 리액트 쿼리 코드에서는 groupAccessCode: password로 전달합니다.
  const [password, setPassword] = useState('');
  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const [revieweeNameErrorMessage, setRevieweeNameErrorMessage] = useState('');
  const [projectNameErrorMessage, setProjectNameErrorMessage] = useState('');

  const mutation = usePostDataForReviewRequestCode();
  const { isOff, handleEyeButtonToggle } = useEyeButton();
  const { isOpen, openModal, closeModal } = useModals();
  const { passwordErrorMessage, handlePasswordBlur } = usePasswordValidation(password);

  const isFormValid =
    isValidReviewGroupDataInput(revieweeName) &&
    isValidReviewGroupDataInput(projectName) &&
    !isNotEmptyInput(passwordErrorMessage); // NOTE: 에러 메세지가 빈 문자열이라면 비밀번호는 유효하다.
  // TODO: 현재 비밀번호만 다른 방식으로 유효성 검증을 하고 있으므로
  // 코드의 통일성을 위해 revieweeName, projectName에 대한 검증도 비밀번호와 비슷한 형식으로 리팩토링하기

  const postDataForURL = () => {
    const dataForReviewRequestCode: DataForReviewRequestCode = { revieweeName, projectName, groupAccessCode: password };

    mutation.mutate(dataForReviewRequestCode, {
      onSuccess: (data) => {
        const completeReviewZoneURL = getCompleteReviewZoneURL(data.reviewRequestCode);
        setReviewZoneURL(completeReviewZoneURL);

        resetInputs();
      },
    });
  };

  const resetInputs = () => {
    setRevieweeName('');
    setProjectName('');
    setPassword('');
  };

  const getCompleteReviewZoneURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/${ROUTE.reviewZone}/${reviewRequestCode}`;
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
  }, [projectName]);

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
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
              onBlur={handlePasswordBlur}
              type={isOff ? 'password' : 'text'}
              placeholder="abc123"
              $style={{ width: '100%', paddingRight: '3rem' }}
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
          <ReviewZoneURLModal reviewZoneURL={reviewZoneURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
        )}
      </FormLayout>
    </S.URLGeneratorForm>
  );
};

export default URLGeneratorForm;
