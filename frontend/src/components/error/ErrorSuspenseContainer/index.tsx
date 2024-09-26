import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { lazy, Suspense } from 'react';
import { ErrorBoundary, FallbackProps } from 'react-error-boundary';

import { EssentialPropsWithChildren } from '@/types';

import ErrorFallback from '../ErrorFallback';

const LoadingPage = lazy(() => import('@/pages/LoadingPage'));

interface ErrorSuspenseContainerProps {
  fallback?: React.ComponentType<FallbackProps>;
}

const ErrorSuspenseContainer = ({
  children,
  fallback = ErrorFallback,
}: EssentialPropsWithChildren<ErrorSuspenseContainerProps>) => {
  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary FallbackComponent={fallback} onReset={reset}>
          <Suspense fallback={<LoadingPage />}>{children}</Suspense>
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default ErrorSuspenseContainer;
