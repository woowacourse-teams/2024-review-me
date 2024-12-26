import React, { useId, useState } from 'react';

import { Button, ErrorSuspenseContainer } from '@/components';
import { ROUTE } from '@/constants/route';
import { useModals } from '@/hooks';
import { isValidPasswordInput, isValidReviewGroupDataInput } from '@/pages/HomePage/utils/validateInput';

import { FormLayout, ReviewZoneURLModal } from '../index';
import { ProjectNameField, RevieweeNameField, PasswordField } from '../Inputs';

import URLGeneratorButton from './components/URLGeneratorButton';
import * as S from './styles';

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const LINK_API_ERROR_MESSAGE = '리뷰 링크 생성에 실패했어요.';
interface URLGeneratorFormProps {
  isMember?: boolean;
}
const URLGeneratorForm = ({ isMember = false }: URLGeneratorFormProps) => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [password, setPassword] = useState('');
  const [apiErrorMessage, setApiErrorMessage] = useState('');

  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const { isOpen, openModal, closeModal } = useModals();
  let apiErrorMessageTime: ReturnType<typeof setTimeout>;

  const useInputId = useId();
  const INPUT_ID = {
    revieweeName: `reviewee-name-input-${useInputId}`,
    projectName: `project-name-input-${useInputId}`,
    password: `password-input-${useInputId}`,
  };

  const isCommonFormValid = isValidReviewGroupDataInput(revieweeName) && isValidReviewGroupDataInput(projectName);

  const isFormValid = isMember ? isCommonFormValid : isCommonFormValid && isValidPasswordInput(password);

  const resetForm = () => {
    setRevieweeName('');
    setProjectName('');
    !isMember && setPassword('');
  };

  const getCompleteReviewZoneURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/${ROUTE.reviewZone}/${reviewRequestCode}`;
  };

  const toastApiErrorMessage = () => {
    setApiErrorMessage(LINK_API_ERROR_MESSAGE);
    apiErrorMessageTime = setTimeout(() => {
      setApiErrorMessage('');
    }, 1000 * 3);
  };
  const handleAPISuccess = (data: any) => {
    const completeReviewZoneURL = getCompleteReviewZoneURL(data.reviewRequestCode);
    setReviewZoneURL(completeReviewZoneURL);

    resetForm();

    setApiErrorMessage('');
    clearTimeout(apiErrorMessageTime);

    openModal(MODAL_KEYS.confirm);
  };

  const handleAPIError = (error: Error) => {
    console.error(error.message);

    toastApiErrorMessage();
    closeModal(MODAL_KEYS.confirm);
  };

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
        <RevieweeNameField id={INPUT_ID.revieweeName} value={revieweeName} setValue={setRevieweeName} />
        <ProjectNameField id={INPUT_ID.projectName} value={projectName} setValue={setProjectName} />
        {!isMember && <PasswordField id={INPUT_ID.password} value={password} setValue={setPassword} />}
        <ErrorSuspenseContainer
          suspenseFallback={
            <Button type="button" styleType="primary" disabled={true}>
              리뷰 링크 생성 중...
            </Button>
          }
        >
          <URLGeneratorButton
            isFormValid={isFormValid}
            dataForReviewRequestCode={{ revieweeName, projectName, groupAccessCode: password }}
            handleAPIError={handleAPIError}
            handleAPISuccess={handleAPISuccess}
          />
        </ErrorSuspenseContainer>
        {apiErrorMessage && <p>{apiErrorMessage}</p>}
        {isOpen(MODAL_KEYS.confirm) && (
          <ReviewZoneURLModal reviewZoneURL={reviewZoneURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
        )}
      </FormLayout>
    </S.URLGeneratorForm>
  );
};

export default React.memo(URLGeneratorForm);
