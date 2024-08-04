import { useState } from 'react';

import { DataForURL } from '@/apis/group';
import { Button, Input } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { debounce } from '@/utils/debounce';

import usePostDataForURL from '../../queries/usePostDataForURL';
import FormLayout from '../FormLayout';
import ReviewGroupDataModal from '../ReviewGroupDataModal';

// TODO: 디바운스 시간을 모든 경우에 0.3초로 고정할 것인지(전역 상수로 사용) 논의하기
const DEBOUNCE_TIME = 300;

const URLGeneratorForm = () => {
  const [revieweeName, setRevieweeName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [reviewRequestCode, setReviewRequestCode] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const mutation = usePostDataForURL();
  const { updateGroupAccessCode } = useGroupAccessCode();

  const getCompleteURL = (reviewRequestCode: string) => {
    return `${window.location.origin}/user/review-writing/${reviewRequestCode}`;
  };

  const handleNameInputChange = (value: string) => {
    setRevieweeName(value);
  };

  const handleProjectNameInputChange = (value: string) => {
    setProjectName(value);
  };

  const handleUrlCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    if (revieweeName && projectName) {
      const dataForURL: DataForURL = { revieweeName, projectName };

      mutation.mutate(dataForURL, {
        onSuccess: (data) => {
          const completeURL = getCompleteURL(data.reviewRequestCode);
          setReviewRequestCode(completeURL);
          updateGroupAccessCode(data.groupAccessCode);
          setIsModalOpen(true);
        },
      });
    }
  }, DEBOUNCE_TIME);

  return (
    <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
      <Input value={revieweeName} onChange={handleNameInputChange} type="text" placeholder="이름을 입력해주세요" />
      <Input
        value={projectName}
        onChange={handleProjectNameInputChange}
        type="text"
        placeholder="함께한 프로젝트 이름을 입력해주세요"
      />
      <Button
        type="button"
        styleType={revieweeName && projectName ? 'primary' : 'disabled'}
        onClick={handleUrlCreationButtonClick}
        disabled={!(revieweeName && projectName)}
      >
        리뷰 요청 URL 생성하기
      </Button>
      {isModalOpen && (
        <ReviewGroupDataModal reviewRequestCode={reviewRequestCode} closeModal={() => setIsModalOpen(false)} />
      )}
    </FormLayout>
  );
};

export default URLGeneratorForm;
