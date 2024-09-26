import { Global, ThemeProvider } from '@emotion/react';
import * as Sentry from '@sentry/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React, { lazy, Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';

import { ErrorSuspenseContainer } from './components';
import { API_ERROR_MESSAGE, ROUTE_PARAM } from './constants';
import { ROUTE } from './constants/route';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

const isProduction = process.env.NODE_ENV === 'production';
const baseUrlPattern = new RegExp(`^${process.env.API_BASE_URL?.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')}`);

const HomePage = lazy(() => import('@/pages/HomePage'));
const DetailedReviewPage = lazy(() => import('@/pages/DetailedReviewPage'));
const ErrorPage = lazy(() => import('@/pages/ErrorPage'));
const ReviewListPage = lazy(() => import('@/pages/ReviewListPage'));
const ReviewWritingCompletePage = lazy(() => import('@/pages/ReviewWritingCompletePage'));
const ReviewWritingPage = lazy(() => import('@/pages/ReviewWritingPage'));
const ReviewZonePage = lazy(() => import('@/pages/ReviewZonePage'));

const LoadingPage = lazy(() => import('@/pages/LoadingPage'));

Sentry.init({
  dsn: `${process.env.SENTRY_DSN}`,
  enabled: isProduction,
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
      refetchOnWindowFocus: false,
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
    element: (
      <Suspense fallback={<LoadingPage />}>
        <App />
      </Suspense>
    ),
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
        path: `${ROUTE.reviewList}/:${ROUTE_PARAM.reviewRequestCode}`,
        element: <ReviewListPage />,
      },
      {
        path: `${ROUTE.detailedReview}/:${ROUTE_PARAM.reviewRequestCode}/:${ROUTE_PARAM.reviewId}`,
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
  if (!isProduction) {
    const { worker } = await import('./mocks/browser');
    return worker.start();
  }
}

enableMocking().then(() => {
  root.render(
    <React.StrictMode>
      <QueryClientProvider client={queryClient}>
        <ThemeProvider theme={theme}>
          <Global styles={(theme) => globalStyles(theme)} />
          <RecoilRoot>
            <RouterProvider router={router} />
          </RecoilRoot>
        </ThemeProvider>
      </QueryClientProvider>
    </React.StrictMode>,
  );
});
