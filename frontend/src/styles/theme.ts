import { Theme } from '@emotion/react';
import { ThemeProperty } from '../types';
import { CSSProperties } from 'react';

// NOTE: 1rem = 10px
export const fontSize: ThemeProperty<CSSProperties['fontSize']> = {
  basic: '1.6rem',
};

export const fontWeight: ThemeProperty<CSSProperties['fontWeight']> = {
  normal: '400',
  medium: '500', // NOTE: 기본 weight
  semibold: '600',
  bold: '700',
};

export const colors: ThemeProperty<CSSProperties['color']> = {
  primary: '#7361DF',
  primaryHover: '#E6E3F6',
  black: '#1E2022',
  white: '#FFFFFF',
  lightGray: '#F1F2F4',
  placeholder: '#D3D3D3',
  gray: '#8D8C8C',
  sidebarBackground: '#F5F5F5',
};

export const zIndex: ThemeProperty<CSSProperties['zIndex']> = {};

const theme: Theme = {
  fontSize,
  fontWeight,
  zIndex,
  colors,
};

export default theme;
