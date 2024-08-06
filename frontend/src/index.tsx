import { Global, ThemeProvider } from '@emotion/react';
import * as Sentry from '@sentry/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';

import { DEV_ENVIRONMENT } from './constants';
import DetailedReviewPage from './pages/DetailedReviewPage';
import ErrorPage from './pages/ErrorPage';
import LandingPage from './pages/LandingPage';
import ReviewListPage from './pages/ReviewListPage';
import ReviewWritingPage from './pages/ReviewWriting';
import ReviewWritingCompletePage from './pages/ReviewWritingCompletePage';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

const { hostname, port } = DEV_ENVIRONMENT;
const isDev = window?.location.hostname === hostname && window.location.port === port;
const baseUrlPattern = new RegExp(`^${process.env.API_BASE_URL?.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')}`);

Sentry.init({
  dsn: `${process.env.SENTRY_DSN}`,
  enabled: !isDev,
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  environment: 'production',
  tracesSampleRate: 1.0,
  tracePropagationTargets: [baseUrlPattern],
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,
});

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
        path: 'user/review-list',
        element: <ReviewListPage />,
      },
      {
        path: 'user/detailed-review/:reviewId',
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
  if (isDev) {
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
