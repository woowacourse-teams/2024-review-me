import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { EssentialPropsWithChildren } from '@/types';

const queryClient = new QueryClient();

const queryClientWrapper = ({ children }: EssentialPropsWithChildren) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

export default queryClientWrapper;
