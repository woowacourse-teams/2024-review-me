import { useId, useState } from 'react';

import { DataForReviewRequestCode } from '@/apis/group';
import { Button } from '@/components';
import { ROUTE } from '@/constants/route';
import useModals from '@/hooks/useModals';
import { debounce } from '@/utils/debounce';

import { useURLGeneratorFormState } from '../../hooks';
import usePostDataForReviewRequestCode from '../../queries/usePostDataForReviewRequestCode';
import { FormLayout, ReviewZoneURLModal } from '../index';
import { ProjectNameField, RevieweeNameField, PasswordField } from '../Inputs';

import * as S from './styles';

// TODO: 디바운스 time 기본 인수로 300 줘버리기
// TODO: 디바운스 시간을 모든 경우에 0.3초로 고정할 것인지(전역 상수로 사용) 논의하기
const DEBOUNCE_TIME = 300;

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const URLGeneratorForm = () => {
  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const { formData, isValidForm, initializeIsValidForm, initializeFormData } = useURLGeneratorFormState();
  const { revieweeName, projectName, password } = formData;

  const { isOpen, openModal, closeModal } = useModals();

  const useInputId = useId();
  const INPUT_ID = {
    revieweeName: `reviewee-name-input-${useInputId}`,
    projectName: `project-name-input-${useInputId}`,
    password: `password-input-${useInputId}`,
  };

  const mutation = usePostDataForReviewRequestCode();

  const postDataForURL = () => {
    const dataForReviewRequestCode: DataForReviewRequestCode = { revieweeName, projectName, groupAccessCode: password };

    mutation.mutate(dataForReviewRequestCode, {
      onSuccess: (data) => {
        const completeReviewZoneURL = getCompleteReviewZoneURL(data.reviewRequestCode);
        setReviewZoneURL(completeReviewZoneURL);

        initializeFormData();
        initializeIsValidForm();
      },
    });
  };

  const getCompleteReviewZoneURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/${ROUTE.reviewZone}/${reviewRequestCode}`;
  };

  const handleUrlCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    postDataForURL();
    openModal(MODAL_KEYS.confirm);
  }, DEBOUNCE_TIME);

  return (
    <S.URLGeneratorForm>
      <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
        <RevieweeNameField id={INPUT_ID.revieweeName} />
        <ProjectNameField id={INPUT_ID.projectName} />
        <PasswordField id={INPUT_ID.password} />
        <Button
          type="button"
          styleType={isValidForm ? 'primary' : 'disabled'}
          onClick={handleUrlCreationButtonClick}
          disabled={!isValidForm}
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
