// import Sidebar from './components/Sidebar';
import { Outlet } from 'react-router';
import ReviewWriting from './pages/ReviewWriting';
import { Header } from './components';
import Main from './components/Main';
import { css } from '@emotion/react';
import Sidebar from './components/Sidebar';

const App = () => {
  return (
    <div
      css={css`
        display: flex;
      `}
    >
      <Sidebar />
      <div>
        <Header />
        <Main>
          <Outlet />
        </Main>
        {/* <ReviewWriting /> */}
      </div>
    </div>
  );
};

export default App;
