import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';

const HomePage = lazy(() => import('@/pages/HomePage'));
const DetailedReviewPage = lazy(() => import('@/pages/DetailedReviewPage'));
const ErrorPage = lazy(() => import('@/pages/ErrorPage'));
const ReviewListPage = lazy(() => import('@/pages/ReviewListPage'));
const ReviewWritingCompletePage = lazy(() => import('@/pages/ReviewWritingCompletePage'));
const ReviewWritingPage = lazy(() => import('@/pages/ReviewWritingPage'));
const ReviewZonePage = lazy(() => import('@/pages/ReviewZonePage'));
const ReviewCollectionPage = lazy(() => import('@/pages/ReviewCollectionPage'));
const LoadingPage = lazy(() => import('@/pages/LoadingPage'));

import App from './App';
import { ErrorSuspenseContainer } from './components';
import { ROUTE_PARAM } from './constants';
import { ROUTE } from './constants/route';

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
      { path: `${ROUTE.reviewWriting}/:${ROUTE_PARAM.reviewRequestCode}`, element: <ReviewWritingPage /> },
      {
        path: `${ROUTE.reviewWritingComplete}/:${ROUTE_PARAM.reviewRequestCode}`,
        element: <ReviewWritingCompletePage />,
      },
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
      { path: `${ROUTE.reviewCollection}/:${ROUTE_PARAM.reviewRequestCode}`, element: <ReviewCollectionPage /> },
    ],
  },
]);

export default router;
