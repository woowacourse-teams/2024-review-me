import { Outlet } from 'react-router';

import { PageLayout, Sidebar, Topbar, SideModal, Footer, Main } from './components';
import Breadcrumb from './components/common/Breadcrumb';
import { useSidebar } from './hooks';
import useBreadcrumbPaths from './hooks/useBreadcrumbPaths';

const App = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();

  const breadcrumbPathList = useBreadcrumbPaths();

  return (
    <PageLayout>
      {/* {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden} closeModal={closeSidebar}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )} */}
      <Topbar openSidebar={openSidebar} />
      {breadcrumbPathList.length > 1 && <Breadcrumb pathList={breadcrumbPathList} />}
      <Main>
        <Outlet />
      </Main>
      <Footer />
    </PageLayout>
  );
};

export default App;
