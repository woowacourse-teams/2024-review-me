import { ReactNode } from 'react';

export type EssentialPropsWithChildren<P = object> = P & {
  children: ReactNode;
};
