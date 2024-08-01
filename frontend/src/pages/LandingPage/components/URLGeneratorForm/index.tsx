import { useState } from 'react';

import { getCreatedGroupDataApi } from '@/apis/group';
import { Button, Input } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { debounce } from '@/utils/debounce';

import FormLayout from '../FormLayout';
import ReviewGroupDataModal from '../ReviewGroupDataModal';
const DEBOUNCE_TIME = 300;

const URLGeneratorForm = () => {
  const [name, setName] = useState('');
  const [projectName, setProjectName] = useState('');
  const [reviewRequestCode, setReviewRequestCode] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { updateGroupAccessCode } = useGroupAccessCode();

  const getCreatedGroupData = async (name: string, projectName: string) => {
    const dataForURL = {
      revieweeName: name,
      projectName: projectName,
    };

    const data = await getCreatedGroupDataApi(dataForURL);

    if (data) {
      setReviewRequestCode(data.reviewRequestCode);
      updateGroupAccessCode(data.groupAccessCode);
    }
  };

  const handleNameInputChange = (value: string) => {
    setName(value);
  };

  const handleProjectNameInputChange = (value: string) => {
    setProjectName(value);
  };

  const handleUrlCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    if (name && projectName) {
      getCreatedGroupData(name, projectName);
      setIsModalOpen(true);
    }
  }, DEBOUNCE_TIME);

  return (
    <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="column">
      <Input value={name} onChange={handleNameInputChange} type="text" placeholder="이름을 입력해주세요" />
      <Input
        value={projectName}
        onChange={handleProjectNameInputChange}
        type="text"
        placeholder="함께한 프로젝트 이름을 입력해주세요"
      />
      <Button
        type="button"
        styleType={name && projectName ? 'primary' : 'disabled'}
        onClick={handleUrlCreationButtonClick}
        disabled={!(name && projectName)}
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
