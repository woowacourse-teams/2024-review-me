import { Main, PageLayout, SideModal, Sidebar, Topbar } from '@/components';
import { useSidebar } from '@/hooks';

import ErrorSection from './components/ErrorSection';

const ERROR_MESSAGE = {
  server_unstable: '서버와의 통신이 불안정합니다.',
};

const ErrorPage = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();

  return (
    <PageLayout>
      {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <ErrorSection errorMessage={ERROR_MESSAGE.server_unstable} />
      </Main>
    </PageLayout>
  );
};

export default ErrorPage;
