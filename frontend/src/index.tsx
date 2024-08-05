import { Global, ThemeProvider } from '@emotion/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';

import ErrorSuspenseContainer from './components/error/ErrorSuspenseContainer/index';
import DetailedReviewPage from './pages/DetailedReviewPage';
import ErrorPage from './pages/ErrorPage';
import LandingPage from './pages/LandingPage';
import ReviewPreviewListPage from './pages/ReviewPreviewListPage';
import ReviewWritingPage from './pages/ReviewWriting';
import ReviewWritingCompletePage from './pages/ReviewWritingCompletePage';
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
        path: 'user/review-writing/:reviewRequestId',
        element: <ReviewWritingPage />,
      },
      { path: 'user/review-writing-complete', element: <ReviewWritingCompletePage /> },
      {
        path: 'user/review-preview-list',
        element: <ReviewPreviewListPage />,
      },
      {
        path: 'user/detailed-review/:id',
        element: <DetailedReviewPage />,
      },
      {
        path: 'home',
        element: <LandingPage />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

async function enableMocking() {
  if (process.env.MSW) {
    const { worker } = await import('./mocks/browser');
    return worker.start();
  }
}

enableMocking().then(() => {
  root.render(
    <React.StrictMode>
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={theme}>
          <Global styles={globalStyles} />
          <RecoilRoot>
            <RouterProvider router={router} />
          </RecoilRoot>
        </ThemeProvider>
      </QueryClientProvider>
    </React.StrictMode>,
  );
});
// root.render(
//   <React.StrictMode>
//     <QueryClientProvider client={queryClient}>
//       <ThemeProvider theme={theme}>
//         <Global styles={globalStyles} />
//         <RecoilRoot>
//           <RouterProvider router={router} />
//         </RecoilRoot>
//       </ThemeProvider>
//     </QueryClientProvider>
//   </React.StrictMode>,
// );
