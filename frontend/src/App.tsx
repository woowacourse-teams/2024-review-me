import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar, SideModal } from './components';
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
    </PageLayout>
  );
};

export default App;
