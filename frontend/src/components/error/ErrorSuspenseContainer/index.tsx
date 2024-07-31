import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';

import LoadingPage from '@/pages/LoadingPage';
import { EssentialPropsWithChildren } from '@/types';

import ErrorFallback from '../ErrorFallback';

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
