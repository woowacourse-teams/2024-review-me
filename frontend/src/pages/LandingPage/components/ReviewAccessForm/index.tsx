import { useState } from 'react';
import { useNavigate } from 'react-router';

import { getIsValidGroupAccessCodeApi } from '@/apis/group';
import { Input, Button } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { debounce } from '@/utils/debounce';

import { FormLayout } from '../index';

import * as S from './styles';

const DEBOUNCE_TIME = 300;

const ReviewAccessForm = () => {
  const [groupAccessCode, setGroupAccessCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();
  const { updateGroupAccessCode } = useGroupAccessCode();

  const isValidGroupAccessCode = async () => {
    const isValid = await getIsValidGroupAccessCodeApi(groupAccessCode);
    return isValid;
  };

  const isAlphanumeric = (groupAccessCode: string) => {
    const alphanumericRegex = /^[A-Za-z0-9]*$/;
    return alphanumericRegex.test(groupAccessCode);
  };

  const handleGroupAccessCodeInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setGroupAccessCode(event.target.value);
  };

  const handleAccessReviewButtonClick = debounce(async (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    try {
      if (!isAlphanumeric(groupAccessCode)) {
        setErrorMessage('알파벳 대소문자와 숫자만 입력 가능합니다.');
        return;
      }

      await isValidGroupAccessCode();

      updateGroupAccessCode(groupAccessCode);
      setErrorMessage('');

      navigate('/user/review-preview-list');
    } catch (error) {
      if (error instanceof Error) setErrorMessage(error.message);
    }
  }, DEBOUNCE_TIME);

  return (
    <>
      <FormLayout title="팀원이 작성한 리뷰를 확인해보세요!" direction="row">
        <S.ReviewAccessFormContent>
          <S.ReviewAccessFormBody>
            <Input
              value={groupAccessCode}
              onChange={handleGroupAccessCodeInputChange}
              type="text"
              placeholder="확인 코드를 입력해주세요."
              $style={{ width: '18rem' }}
            />
            <Button
              type="button"
              styleType={groupAccessCode ? 'primary' : 'disabled'}
              onClick={handleAccessReviewButtonClick}
              disabled={!groupAccessCode}
            >
              리뷰 확인하기
            </Button>
          </S.ReviewAccessFormBody>
          {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
        </S.ReviewAccessFormContent>
      </FormLayout>
    </>
  );
};

export default ReviewAccessForm;
