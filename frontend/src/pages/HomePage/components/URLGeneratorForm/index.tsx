import { useEffect, useState } from 'react';

import { DataForURL } from '@/apis/group';
import { Button, Input } from '@/components';
import { useGroupAccessCode } from '@/hooks';
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
  const [reviewRequestCode, setReviewRequestCode] = useState('');

  const [revieweeNameErrorMessage, setRevieweeNameErrorMessage] = useState('');
  const [projectNameErrorMessage, setProjectNameErrorMessage] = useState('');

  const mutation = usePostDataForURL();
  const { updateGroupAccessCode } = useGroupAccessCode();
  const { isOpen, openModal, closeModal } = useModals();

  const isFormValid = isValidReviewGroupDataInput(revieweeName) && isValidReviewGroupDataInput(projectName);

  const postDataForURL = () => {
    const dataForURL: DataForURL = { revieweeName, projectName };

    mutation.mutate(dataForURL, {
      onSuccess: (data) => {
        const completeURL = getCompleteURL(data.reviewRequestCode);

        setReviewRequestCode(completeURL);
        updateGroupAccessCode(data.groupAccessCode);

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
    <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
      <S.InputContainer>
        <Input value={revieweeName} onChange={handleNameInputChange} type="text" placeholder="이름을 입력해주세요" />
        <S.ErrorMessage>{revieweeNameErrorMessage}</S.ErrorMessage>
      </S.InputContainer>
      <S.InputContainer>
        <Input
          value={projectName}
          onChange={handleProjectNameInputChange}
          type="text"
          placeholder="함께한 프로젝트 이름을 입력해주세요"
        />
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
        <ReviewGroupDataModal reviewRequestCode={reviewRequestCode} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
      )}
    </FormLayout>
  );
};

export default URLGeneratorForm;
