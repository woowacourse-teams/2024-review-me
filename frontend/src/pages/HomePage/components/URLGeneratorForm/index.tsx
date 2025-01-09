import React, { useId, useState } from 'react';

import AlertIcon from '@/assets/alertTriangle.svg';
import { ErrorSuspenseContainer, Toast } from '@/components';
import { ROUTE } from '@/constants/route';
import { useModals } from '@/hooks';

import { FormLayout, ReviewZoneURLModal } from '../index';
import { PasswordField, ReviewGroupDataField } from '../Inputs';

import URLGeneratorButton from './components/URLGeneratorButton';
import useURLGeneratorState from './hooks/useURLGeneratorState';
import * as S from './styles';

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const TOAST_INFORM = {
  icon: { src: AlertIcon, alt: '' },
  message: '리뷰 링크 생성에 실패했어요. 다시 시도해 보세요.',
  duration: 1000 * 3,
};
interface URLGeneratorFormProps {
  isMember?: boolean;
}
const URLGeneratorForm = ({ isMember = false }: URLGeneratorFormProps) => {
  const { revieweeName, projectName, password, isFormValid, resetForm, urlGeneratorStateUpdater } =
    useURLGeneratorState({ isMember });

  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const [isOpenToast, setIsOpenToast] = useState(false);
  const { isOpen, openModal, closeModal } = useModals();

  const handleOpenToast = (isOpen: boolean) => setIsOpenToast(isOpen);

  const useInputId = useId();

  const INPUT_ID = {
    revieweeName: `reviewee-name-input-${useInputId}`,
    projectName: `project-name-input-${useInputId}`,
    password: `password-input-${useInputId}`,
  };

  const getCompleteReviewZoneURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/${ROUTE.reviewZone}/${reviewRequestCode}`;
  };

  const handleAPISuccess = (data: any) => {
    const completeReviewZoneURL = getCompleteReviewZoneURL(data.reviewRequestCode);
    setReviewZoneURL(completeReviewZoneURL);

    resetForm();

    handleOpenToast(false);
    openModal(MODAL_KEYS.confirm);
  };

  const handleAPIError = (error: Error) => {
    console.error(error.message);

    handleOpenToast(true);
    closeModal(MODAL_KEYS.confirm);
  };

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
        <ReviewGroupDataField
          id={INPUT_ID.revieweeName}
          labelText="본인의 이름을 적어주세요"
          value={revieweeName}
          updateValue={urlGeneratorStateUpdater.revieweeName}
        />
        <ReviewGroupDataField
          id={INPUT_ID.projectName}
          labelText="함께한 프로젝트 이름을 입력해주세요"
          value={projectName}
          updateValue={urlGeneratorStateUpdater.projectName}
        />
        {!isMember && (
          <PasswordField id={INPUT_ID.password} value={password} updateValue={urlGeneratorStateUpdater.password} />
        )}
        <ErrorSuspenseContainer>
          <URLGeneratorButton
            isFormValid={isFormValid}
            dataForReviewRequestCode={{ revieweeName, projectName, groupAccessCode: password }}
            handleAPIError={handleAPIError}
            handleAPISuccess={handleAPISuccess}
          />
        </ErrorSuspenseContainer>
        {isOpenToast && (
          <Toast
            icon={TOAST_INFORM.icon}
            message={TOAST_INFORM.message}
            handleOpenModal={handleOpenToast}
            duration={TOAST_INFORM.duration}
            position="bottom"
          />
        )}
        {isOpen(MODAL_KEYS.confirm) && (
          <ReviewZoneURLModal reviewZoneURL={reviewZoneURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
        )}
      </FormLayout>
    </S.URLGeneratorForm>
  );
};

export default URLGeneratorForm;
