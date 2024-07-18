import App from '@/App';
import { css, Global } from '@emotion/react';
import { ThemeProvider } from '@emotion/react';

import React from 'react';
import ReactDOM from 'react-dom/client';
import reset from './styles/reset';
import globalStyles from './styles/globalStyles';
import ReviewWriting from './pages/ReviewWriting';
import DetailedReviewPage from './pages/DetailedReviewPage';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import theme from './styles/theme';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: 'user',
        element: <div>user</div>,
      },

      {
        path: 'user/review-writing',
        element: <ReviewWriting />,
      },
      {
        path: 'user/detailed-review',
        element: <DetailedReviewPage />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <Global styles={globalStyles} />
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>,
);
