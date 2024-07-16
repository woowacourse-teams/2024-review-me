import App from '@/App';
import { css, Global } from '@emotion/react';

import React from 'react';
import ReactDOM from 'react-dom/client';
import reset from './styles/reset';
import globalStyles from './styles/globalStyles';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <Global
      styles={css`
        ${reset()} ${globalStyles}
      `}
    />
  </React.StrictMode>,
);
