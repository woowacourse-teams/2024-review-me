import { useState } from 'react';

import { Input, Button } from '@/components';

import * as S from '../../styles';
import FormLayout from '../FormLayout';

const ReviewAccessForm = () => {
  const [groupAccessCode, setGroupAccessCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleGroupAccessCodeInputChange = (value: string) => {
    setGroupAccessCode(value);
  };

  const handleAccessReviewButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    if (!groupAccessCode) {
      setErrorMessage('확인 코드를 입력해주세요.');
    } else {
      console.log('클릭');
      setGroupAccessCode('');
      setErrorMessage('');
    }
  };

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
