import { Global, ThemeProvider } from '@emotion/react';
import * as Sentry from '@sentry/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';
import {
  DetailedReviewPage,
  ErrorPage,
  HomePage,
  ReviewListPage,
  ReviewWritingCompletePage,
  ReviewWritingPage,
  ReviewZonePage,
} from '@/pages';

import { ErrorSuspenseContainer } from './components';
import { API_ERROR_MESSAGE, DEV_ENVIRONMENT, ROUTE_PARAM } from './constants';
import { ROUTE } from './constants/route';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

const { hostname, port } = DEV_ENVIRONMENT;
const isDev = window?.location.hostname === hostname && window.location.port === port;
const baseUrlPattern = new RegExp(`^${process.env.API_BASE_URL?.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')}`);

Sentry.init({
  dsn: `${process.env.SENTRY_DSN}`,
  enabled: !isDev,
  integrations: [Sentry.browserTracingIntegration()],
  environment: 'production',
  tracesSampleRate: 1.0,
  tracePropagationTargets: [baseUrlPattern],
});

export function retryFunction(failureCount: number, error: Error): boolean {
  const { message } = error;
  const isServerError = message === API_ERROR_MESSAGE.serverError;

  // Fetch API로 인해 발생한 오류인지 확인
  // 500번대 에러이면 한 번 더 재시도
  if (isServerError) return failureCount < 1;

  return false; // 그 외의 경우 재시도하지 않음
}

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      throwOnError: true,
      retry: retryFunction,
    },
    mutations: {
      throwOnError: true,
      retry: retryFunction,
    },
  },
});

const router = createBrowserRouter([
  {
    path: ROUTE.home,
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '',
        element: <HomePage />,
      },
      {
        path: 'user',
        element: <div>user</div>,
      },
      { path: `${ROUTE.reviewWriting}/:${ROUTE_PARAM.reviewRequestCode}`, element: <ReviewWritingPage /> },
      { path: ROUTE.reviewWritingComplete, element: <ReviewWritingCompletePage /> },
      {
        path: ROUTE.reviewList,
        element: <ReviewListPage />,
      },
      {
        path: `${ROUTE.detailedReview}/:${ROUTE_PARAM.reviewId}`,
        element: <DetailedReviewPage />,
      },
      {
        path: `${ROUTE.reviewZone}/:${ROUTE_PARAM.reviewRequestCode}`,
        element: (
          <ErrorSuspenseContainer>
            <ReviewZonePage />
          </ErrorSuspenseContainer>
        ),
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
