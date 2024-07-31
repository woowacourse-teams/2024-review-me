import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar, SideModal, ErrorSuspenseContainer } from './components';
import { useSidebar } from './hooks';

const App = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();

  return (
    <PageLayout>
      {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden} closeModal={closeSidebar}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <ErrorSuspenseContainer>
          <Outlet />
        </ErrorSuspenseContainer>
      </Main>
    </PageLayout>
  );
};

export default App;
