import { GitHubLoginButton } from '@/components';

import { ContentLayout } from '../layouts';

const LoginPrompt = () => {
  return (
    <ContentLayout
      title="함께한 팀원으로부터 리뷰를 받아보세요!"
      subTitleList={[
        '로그인을 하면 리뷰 링크를 직접 관리하지 않아도 돼요.',
        '내가 작성한 리뷰와 받은 리뷰를 편하게 관리해 보세요.',
      ]}
    >
      <GitHubLoginButton action="none" />
    </ContentLayout>
  );
};

export default LoginPrompt;
