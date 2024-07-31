import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { Suspense } from 'react';
import { ErrorBoundary, FallbackProps } from 'react-error-boundary';
import { Outlet } from 'react-router';

import { Main, PageLayout, Sidebar, Topbar, SideModal } from './components';
import { useSidebar } from './hooks';

// 에러가 발생했을 때 보여줄 Fallback 컴포넌트
function ErrorFallback({ error, resetErrorBoundary }: FallbackProps) {
  return (
    <div role="alert">
      <p>오류:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>재시도</button>
    </div>
  );
}

const App = () => {
  const { isSidebarHidden, isSidebarModalOpen, closeSidebar, openSidebar } = useSidebar();

  return (
    <PageLayout>
      {isSidebarModalOpen && (
        <SideModal isSidebarHidden={isSidebarHidden} closeModal={closeSidebar}>
          <Sidebar closeSidebar={closeSidebar} />
        </SideModal>
      )}
      <Topbar openSidebar={openSidebar} />
      <Main>
        <QueryErrorResetBoundary>
          {({ reset }) => (
            <ErrorBoundary FallbackComponent={ErrorFallback} onReset={reset}>
              <Suspense fallback={<div>로딩 중....</div>}>
                <Outlet />
              </Suspense>
            </ErrorBoundary>
          )}
        </QueryErrorResetBoundary>
      </Main>
    </PageLayout>
  );
};

export default App;
