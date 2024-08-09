import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar, SideModal } from './components';
import { useSidebar } from './hooks';

const App = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();

  return (
    <PageLayout>
      {/* {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden} closeModal={closeSidebar}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )} */}
      <Topbar openSidebar={openSidebar} />
      <div>주문하신 CD 반영입니다~</div>
      <Main>
        <Outlet />
      </Main>
    </PageLayout>
  );
};

export default App;
