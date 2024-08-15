import { useEffect, useState } from 'react';

import { DataForURL } from '@/apis/group';
import { Button, Input } from '@/components';
import useModals from '@/hooks/useModals';
import { debounce } from '@/utils/debounce';

import usePostDataForURL from '../../queries/usePostDataForURL';
import { isValidReviewGroupDataInput, isWithinMaxLength } from '../../utils/validateInput';
import { FormLayout, ReviewGroupDataModal } from '../index';

import * as S from './styles';

// TODO: 디바운스 시간을 모든 경우에 0.3초로 고정할 것인지(전역 상수로 사용) 논의하기
const DEBOUNCE_TIME = 300;
const MAX_VALID_REVIEW_GROUP_DATA_INPUT = 50;

const LENGTH_ERROR_MESSAGE = `${MAX_VALID_REVIEW_GROUP_DATA_INPUT}자까지 입력할 수 있습니다.`;

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
  const { isOpen, openModal, closeModal } = useModals();

  const isFormValid = isValidReviewGroupDataInput(revieweeName) && isValidReviewGroupDataInput(projectName);

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
    isWithinMaxLength(revieweeName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setRevieweeNameErrorMessage('')
      : setRevieweeNameErrorMessage(LENGTH_ERROR_MESSAGE);
  }, [revieweeName]);

  useEffect(() => {
    isWithinMaxLength(projectName, MAX_VALID_REVIEW_GROUP_DATA_INPUT)
      ? setProjectNameErrorMessage('')
      : setProjectNameErrorMessage(LENGTH_ERROR_MESSAGE);
  }, [projectName]);

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원에게 리뷰를 받아보세요!" direction="column">
        <S.InputContainer>
          <S.Label htmlFor="reviewee-name">리뷰 받을 사람의 이름을 적어주세요</S.Label>
          <Input
            id="reviewee-name"
            value={revieweeName}
            onChange={handleNameInputChange}
            type="text"
            placeholder="행성이"
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
          <S.Label htmlFor="password">리뷰 확인을 위한 비밀번호를 적어주세요</S.Label>
          <Input id="password" value={password} onChange={handlePasswordInputChange} type="text" placeholder="abc123" />
          <S.ErrorMessage>{projectNameErrorMessage}</S.ErrorMessage>
        </S.InputContainer>
        <Button
          type="button"
          styleType={isFormValid ? 'primary' : 'disabled'}
          onClick={handleUrlCreationButtonClick}
          disabled={!isFormValid}
        >
          리뷰 요청 URL 생성하기
        </Button>
        {isOpen(MODAL_KEYS.confirm) && (
          <ReviewGroupDataModal reviewURL={reviewURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
        )}
      </FormLayout>
    </S.URLGeneratorForm>
  );
};

export default URLGeneratorForm;
