import { Outlet } from 'react-router';
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
      </div>
    </div>
  );
};

export default App;
