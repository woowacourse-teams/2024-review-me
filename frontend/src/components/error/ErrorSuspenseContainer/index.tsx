import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { lazy, ReactNode, Suspense } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import ErrorBoundary, { FallbackProps } from '../ErrorBoundary';
import ErrorFallback from '../ErrorFallback';

const LoadingPage = lazy(() => import('@/pages/LoadingPage'));

interface ErrorSuspenseContainerProps {
  errorFallback?: React.ComponentType<FallbackProps>;
  suspenseFallback?: ReactNode;
}

const ErrorSuspenseContainer = ({
  children,
  errorFallback = ErrorFallback,
  suspenseFallback = <LoadingPage />,
}: EssentialPropsWithChildren<ErrorSuspenseContainerProps>) => {
  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary fallback={errorFallback} resetQueryError={reset}>
          <Suspense fallback={suspenseFallback}>{children}</Suspense>
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default ErrorSuspenseContainer;
