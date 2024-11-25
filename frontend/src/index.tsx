import { Global, ThemeProvider } from '@emotion/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import router from './router';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';
import { initializeSentry, retryQuery, startAmplitude, startMockWorker } from './utils';

initializeSentry();
startAmplitude();

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      throwOnError: true,
      retry: retryQuery,
      refetchOnWindowFocus: false,
    },
    mutations: {
      throwOnError: true,
      retry: retryQuery,
    },
  },
});

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

startMockWorker().then(() => {
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
