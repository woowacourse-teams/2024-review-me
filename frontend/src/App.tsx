import { Outlet } from 'react-router';

import { PageLayout } from './components';
import { useTrackVisitedPageInAmplitude } from './hooks';

const App = () => {
  useTrackVisitedPageInAmplitude();

  return (
    <PageLayout>
      <Outlet />
    </PageLayout>
  );
};

export default App;
