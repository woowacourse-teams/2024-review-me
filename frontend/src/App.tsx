import { useState } from 'react';
import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar } from './components';

const App = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [isSidebarHidden, setIsSidebarHidden] = useState(true);

  const closeSidebar = () => {
    setIsSidebarOpen(false);
    setTimeout(() => {
      setIsSidebarHidden(true);
    }, 1000);
  };

  const openSidebar = () => {
    setIsSidebarHidden(false);
    setIsSidebarOpen(true);
  };

  return (
    <PageLayout>
      {!isSidebarHidden && <Sidebar closeSidebar={closeSidebar} isSidebarOpen={isSidebarOpen} />}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <Outlet />
      </Main>
    </PageLayout>
  );
};

export default App;
