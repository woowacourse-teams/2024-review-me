import { Outlet } from 'react-router';

import { PageLayout } from './components';

const App = () => {
  return (
    <PageLayout>
      <Outlet />
    </PageLayout>
  );
};

export default App;
