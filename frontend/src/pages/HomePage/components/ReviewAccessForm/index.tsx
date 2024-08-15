import { useState } from 'react';
import { useNavigate } from 'react-router';

import { getIsValidGroupAccessCodeApi } from '@/apis/group';
import { Input, Button } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { debounce } from '@/utils/debounce';

import { isValidAccessCodeInput } from '../../utils/validateInput';
import { FormLayout } from '../index';

import * as S from './styles';

const DEBOUNCE_TIME = 300;

const ALPHANUMERIC_ERROR_MESSAGE = '알파벳 대소문자와 숫자만 입력 가능합니다.';

// NOTE: groupAccessCode가 유효한지를 확인하는 API 호출은 fetch로 고정!
// 1. 요청을 통해 단순히 true, false 정도의 데이터를 단발적으로 가져오는 API이므로
// 리액트 쿼리를 사용할 만큼 서버 상태를 정교하게 가지고 있을 필요 없음
// 2. 리액트 쿼리를 도입했을 때 Errorboundary로 Form을 감싸지 않았고, useQuery를 사용했음에도 불구하고
// error fallback이 뜨는 버그 존재
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
        setErrorMessage(ALPHANUMERIC_ERROR_MESSAGE);
        return;
      }

      await isValidGroupAccessCode();

      updateGroupAccessCode(groupAccessCode);
      setErrorMessage('');

      navigate('/user/review-list');
    } catch (error) {
      if (error instanceof Error) setErrorMessage(error.message);
    }
  }, DEBOUNCE_TIME);

  return (
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
            styleType={isValidAccessCodeInput(groupAccessCode) ? 'primary' : 'disabled'}
            onClick={handleAccessReviewButtonClick}
            disabled={!groupAccessCode}
          >
            리뷰 확인하기
          </Button>
        </S.ReviewAccessFormBody>
        {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
      </S.ReviewAccessFormContent>
    </FormLayout>
  );
};

export default ReviewAccessForm;
