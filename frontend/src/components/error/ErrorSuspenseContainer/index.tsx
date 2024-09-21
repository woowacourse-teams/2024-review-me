import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { lazy, Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';

import { EssentialPropsWithChildren } from '@/types';

import ErrorFallback from '../ErrorFallback';

const LoadingPage = lazy(() => import('@/pages/LoadingPage'));

const ErrorSuspenseContainer = ({ children }: EssentialPropsWithChildren) => {
  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary FallbackComponent={ErrorFallback} onReset={reset}>
          <Suspense fallback={<LoadingPage />}>{children}</Suspense>
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default ErrorSuspenseContainer;
