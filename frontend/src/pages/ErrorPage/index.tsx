import { useNavigate } from 'react-router';

import { Main, PageLayout, SideModal, Sidebar, Topbar, ErrorSection } from '@/components';
import { ROUTE_ERROR_MESSAGE } from '@/constants';
import { useSidebar } from '@/hooks';

const ErrorPage = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();
  const navigate = useNavigate();

  const handleReload = () => {
    navigate(0);
  };

  const handleGoHome = () => {
    navigate('/'); // TODO: 홈 페이지 경로가 결정되면 변경 필요
  };

  return (
    <PageLayout>
      {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden} closeModal={closeSidebar}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <ErrorSection errorMessage={ROUTE_ERROR_MESSAGE} handleReload={handleReload} handleGoHome={handleGoHome} />
      </Main>
    </PageLayout>
  );
};

export default ErrorPage;
