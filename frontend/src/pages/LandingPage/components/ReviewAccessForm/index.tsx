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
  const navigate = useNavigate();
  const { updateGroupAccessCode } = useGroupAccessCode();

  const [groupAccessCode, setGroupAccessCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const isValidGroupAccessCode = async () => {
    const isValid = await getIsValidGroupAccessCodeApi(groupAccessCode);
    return isValid;
  };

  const handleGroupAccessCodeInputChange = (value: string) => {
    setGroupAccessCode(value);
  };

  const handleAccessReviewButtonClick = debounce(async (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    try {
      await isValidGroupAccessCode();

      updateGroupAccessCode(groupAccessCode);
      setErrorMessage('');
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
