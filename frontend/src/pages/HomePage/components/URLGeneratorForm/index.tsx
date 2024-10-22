import { useId, useState } from 'react';

import { DataForReviewRequestCode } from '@/apis/group';
import { Button } from '@/components';
import { HOM_EVENT_NAME } from '@/constants';
import { ROUTE } from '@/constants/route';
import { useModals } from '@/hooks';
import { isValidPasswordInput, isValidReviewGroupDataInput } from '@/pages/HomePage/utils/validateInput';
import { debounce, trackEventInAmplitude } from '@/utils';

import usePostDataForReviewRequestCode from '../../hooks/usePostDataForReviewRequestCode';
import { FormLayout, ReviewZoneURLModal } from '../index';
import { ProjectNameField, RevieweeNameField, PasswordField } from '../Inputs';

import * as S from './styles';

const DEBOUNCE_TIME = 300;

const MODAL_KEYS = {
  confirm: 'CONFIRM',
};

const URLGeneratorForm = () => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [password, setPassword] = useState('');

  const [reviewZoneURL, setReviewZoneURL] = useState('');

  const { isOpen, openModal, closeModal } = useModals();

  const useInputId = useId();
  const INPUT_ID = {
    revieweeName: `reviewee-name-input-${useInputId}`,
    projectName: `project-name-input-${useInputId}`,
    password: `password-input-${useInputId}`,
  };

  const mutation = usePostDataForReviewRequestCode();

  const isFormValid =
    isValidReviewGroupDataInput(revieweeName) &&
    isValidReviewGroupDataInput(projectName) &&
    isValidPasswordInput(password);

  const postDataForURL = () => {
    trackEventInAmplitude(HOM_EVENT_NAME.generateReviewURL);

    const dataForReviewRequestCode: DataForReviewRequestCode = { revieweeName, projectName, groupAccessCode: password };
    mutation.mutate(dataForReviewRequestCode, {
      onSuccess: (data) => {
        const completeReviewZoneURL = getCompleteReviewZoneURL(data.reviewRequestCode);
        setReviewZoneURL(completeReviewZoneURL);

        resetForm();
      },
    });
  };

  const resetForm = () => {
    setRevieweeName('');
    setProjectName('');
    setPassword('');
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
        <RevieweeNameField id={INPUT_ID.revieweeName} value={revieweeName} setValue={setRevieweeName} />
        <ProjectNameField id={INPUT_ID.projectName} value={projectName} setValue={setProjectName} />
        <PasswordField id={INPUT_ID.password} value={password} setValue={setPassword} />
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
