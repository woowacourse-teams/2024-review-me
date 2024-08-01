import { useState } from 'react';

import { Button, Input } from '@/components';

import FormLayout from '../FormLayout';

const URLGeneratorForm = () => {
  const [name, setName] = useState('');
  const [projectName, setProjectName] = useState('');

  const handleNameInputChange = (value: string) => {
    setName(value);
  };

  const handleProjectNameInputChange = (value: string) => {
    setProjectName(value);
  };

  const handleUrlCreationButtonClick = (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();

    console.log('클릭');

    if (name && projectName) {
      setName('');
      setProjectName('');
    }
  };

  return (
    <FormLayout title="함께한 팀원으로부터 리뷰를 받아보세요!" direction="col">
      <Input value={name} onChange={handleNameInputChange} type="text" placeholder="이름을 입력해주세요" />
      <Input
        value={projectName}
        onChange={handleProjectNameInputChange}
        type="text"
        placeholder="함께한 프로젝트 이름을 입력해주세요"
      />
      <Button
        buttonType={name && projectName ? 'primary' : 'disabled'}
        text="리뷰 요청 URL 생성하기"
        onClick={handleUrlCreationButtonClick}
        disabled={!(name && projectName)}
      />
    </FormLayout>
  );
};

export default URLGeneratorForm;
