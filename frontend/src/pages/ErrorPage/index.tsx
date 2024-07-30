import { useNavigate } from 'react-router';

import { Main, PageLayout, SideModal, Sidebar, Topbar } from '@/components';
import { useSidebar } from '@/hooks';

import ErrorSection from './components/ErrorSection';

const ERROR_MESSAGE = {
  server_unstable: '서버와의 통신이 불안정합니다.',
};

const ErrorPage = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();
  const navigate = useNavigate();

  const handleReLoad = () => {
    window.location.reload();
  };

  const handleGoHome = () => {
    navigate('/'); // TODO: 홈 페이지 경로가 결정되면 변경 필요
  };

  return (
    <PageLayout>
      {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <ErrorSection
          errorMessage={ERROR_MESSAGE.server_unstable}
          handleReLoad={handleReLoad}
          handleGoHome={handleGoHome}
        />
      </Main>
    </PageLayout>
  );
};

export default ErrorPage;
