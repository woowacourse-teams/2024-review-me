import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar } from './components';
import SideModal from './components/common/modals/SideModal';
import { useSidebar } from './hooks';

const App = () => {
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
        <Outlet />
      </Main>
    </PageLayout>
  );
};

export default App;
