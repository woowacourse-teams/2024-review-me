import { Theme } from '@emotion/react';
import { CSSProperties } from 'react';

import { ThemeProperty } from '../types';

export const formWidth = '86.7rem';
export const sidebarWidth: ThemeProperty<string> = {
  desktop: '25rem',
  mobile: '100vw',
};
export const breakpoints: ThemeProperty<string> = {
  desktop: '102.4rem',
  tablet: '72rem',
  mobile: '57.6rem',
};
// NOTE: 1rem = 10px
export const fontSize: ThemeProperty<CSSProperties['fontSize']> = {
  basic: '1.6rem',
  medium: '2.4rem',
  large: '3.2rem',
  h2: '4.8rem',
};

export const borderRadius: ThemeProperty<CSSProperties['borderRadius']> = {
  basic: '0.8rem',
};

export const fontWeight: ThemeProperty<CSSProperties['fontWeight']> = {
  normal: '400',
  medium: '500', // NOTE: 기본 weight
  semibold: '600',
  bold: '700',
  bolder: '800',
};

export const colors: ThemeProperty<CSSProperties['color']> = {
  primary: '#7361DF',
  primaryHover: '#9082E6',
  lightPurple: '#E6E3F6',
  black: '#1E2022',
  white: '#FFFFFF',
  lightGray: '#F1F2F4',
  placeholder: '#D3D3D3',
  gray: '#7F7F7F',
  sidebarBackground: '#F5F5F5',
  disabled: '#D8D8D8',
  disabledText: '#7F7F7F',
};

export const zIndex: ThemeProperty<CSSProperties['zIndex']> = {
  sidebar: 999,
};

const theme: Theme = {
  fontSize,
  fontWeight,
  zIndex,
  colors,
  breakpoints,
  sidebarWidth,
  borderRadius,
  formWidth,
};

export default theme;
