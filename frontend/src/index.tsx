import { Global, ThemeProvider } from '@emotion/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import App from '@/App';

import DetailedReviewPage from './pages/DetailedReviewPage';
import ErrorPage from './pages/ErrorPage';
import ReviewPreviewListPage from './pages/ReviewPreviewListPage';
import ReviewWritingPage from './pages/ReviewWriting';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      throwOnError: true,
    },
    mutations: {
      throwOnError: true,
    },
  },
});

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
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
        path: 'user/review-preview-list',
        element: <ReviewPreviewListPage />,
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
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <Global styles={globalStyles} />
        <RouterProvider router={router} />
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
