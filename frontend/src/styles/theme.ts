import { Theme } from '@emotion/react';
import { CustomTheme } from '../types';
import { CSSProperties } from 'react';

// NOTE: 1rem = 10px
const fontSize: CustomTheme<CSSProperties['fontSize']> = {
  basic: '1.6rem',
};

const fontWeight: CustomTheme<CSSProperties['fontWeight']> = {
  normal: '400',
  medium: '500', // NOTE: 기본 weight
  semibold: '600',
  bold: '700',
};

const colors: CustomTheme<CSSProperties['color']> = {
  primary: '#7361DF',
  primaryHover: '#E6E3F6',
  black: '#1E2022',
  white: '#FFFFFF',
  lightGray: '#F1F2F4',
  placeholder: '#D3D3D3',
  gray: '#8D8C8C',
  sidebarBackground: '#F5F5F5',
};

const zIndex: CustomTheme<CSSProperties['zIndex']> = {};

const theme: Theme = {
  fontSize,
  fontWeight,
  zIndex,
  colors,
};

export default theme;
