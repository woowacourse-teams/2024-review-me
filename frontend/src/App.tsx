import { useState } from 'react';
import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar } from './components';

const App = () => {
  return (
    <PageLayout>
      <Main>
        <Outlet />
      </Main>
    </PageLayout>
  );
};

export default App;
