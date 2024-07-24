import { Global, ThemeProvider } from '@emotion/react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';

import DetailedReviewPage from './pages/DetailedReviewPage';
import ReviewWritingPage from './pages/ReviewWriting';
import globalStyles from './styles/globalStyles';
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
        element: <ReviewWritingPage />,
      },
      {
        path: 'user/detailed-review/:id',
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
