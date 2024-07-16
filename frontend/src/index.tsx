import App from '@/App';
import { css, Global } from '@emotion/react';

import React from 'react';
import ReactDOM from 'react-dom/client';
import reset from './styles/reset';
import globalStyles from './styles/globalStyles';
import ReviewWriting from './pages/ReviewWriting';
import DetailedReviewPage from './pages/DetailedReviewPage';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

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
    <Global
      styles={css`
        ${reset()} ${globalStyles}
      `}
    />
    <RouterProvider router={router} />
  </React.StrictMode>,
);
