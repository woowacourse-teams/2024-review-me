import { useId, useState } from 'react';

import { ErrorSuspenseContainer, ReviewZoneURLModal } from '@/components';
import { ROUTE } from '@/constants/route';
import { useModals, useToastContext } from '@/hooks';

import { PasswordField, ReviewGroupDataField, URLGeneratorButton } from './components';
import useURLGeneratorState from './hooks/useURLGeneratorState';
import * as S from './style';

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

interface URLGeneratorFormProps {
  isMember?: boolean;
  refetchReviewLinks?: () => void;
}
const URLGeneratorForm = ({ isMember = false, refetchReviewLinks }: URLGeneratorFormProps) => {
  const { revieweeName, projectName, password, isFormValid, resetForm, urlGeneratorStateUpdater } =
    useURLGeneratorState({ isMember });

  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const { showToast, hideToast } = useToastContext();

  const { isOpen, openModal, closeModal } = useModals();

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

    refetchReviewLinks?.();

    resetForm();

    hideToast();
    openModal(MODAL_KEYS.confirm);
  };

  const handleAPIError = (error: Error) => {
    showToast({ type: 'confirm', message: '리뷰 링크 생성에 실패했어요. 다시 시도해 보세요.' });
    closeModal(MODAL_KEYS.confirm);
  };

  return (
    <>
      <S.URLGeneratorForm>
        <S.Title>함께한 팀원으로부터 리뷰를 받아보세요!</S.Title>
        <S.Fieldset>
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
        </S.Fieldset>
        <ErrorSuspenseContainer>
          <URLGeneratorButton
            isFormValid={isFormValid}
            dataForReviewRequestCode={{ revieweeName, projectName, groupAccessCode: password }}
            handleAPIError={handleAPIError}
            handleAPISuccess={handleAPISuccess}
          />
        </ErrorSuspenseContainer>
      </S.URLGeneratorForm>
      {isOpen(MODAL_KEYS.confirm) && (
        <ReviewZoneURLModal reviewZoneURL={reviewZoneURL} closeModal={() => closeModal(MODAL_KEYS.confirm)} />
      )}
    </>
  );
};

export default URLGeneratorForm;
