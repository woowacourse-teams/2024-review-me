import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { lazy, Suspense } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import ErrorBoundary, { FallbackProps } from '../ErrorBoundary';
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
        <ErrorBoundary fallback={fallback} resetQueryError={reset}>
          <Suspense fallback={<LoadingPage />}>{children}</Suspense>
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default ErrorSuspenseContainer;
