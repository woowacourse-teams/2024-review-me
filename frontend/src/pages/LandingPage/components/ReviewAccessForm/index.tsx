import { useState } from 'react';
import { useNavigate } from 'react-router';

import { checkGroupAccessCodeApi } from '@/apis/review';
import { Input, Button } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { debounce } from '@/utils/debounce';

import * as S from '../../styles';
import FormLayout from '../FormLayout';

const DEBOUNCE_TIME = 300;

const ReviewAccessForm = () => {
  const navigate = useNavigate();
  const { updateGroupAccessCode } = useGroupAccessCode();

  const [groupAccessCode, setGroupAccessCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const isValidGroupAccessCode = async () => {
    const isValid = await checkGroupAccessCodeApi(groupAccessCode);
    return isValid;
  };

  const handleGroupAccessCodeInputChange = (value: string) => {
    setGroupAccessCode(value);
  };

  const handleAccessReviewButtonClick = debounce(async (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    try {
      const isValid = await isValidGroupAccessCode();

      if (isValid) {
        updateGroupAccessCode(groupAccessCode);
        setErrorMessage('');
        
        navigate('/user/review-preview-list');
      } else {
        setErrorMessage('유효하지 않은 그룹 접근 코드입니다.');
      }
    } catch (error) {
      setErrorMessage('오류가 발생했습니다. 다시 시도해주세요.');
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
