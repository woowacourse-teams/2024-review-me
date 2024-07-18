import '@emotion/react';
import { fontSize } from '@/styles/theme';

export type ThemeProperty<T> = {
  [key: string]: T;
};
