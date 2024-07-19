import '@emotion/react';
import { colors, fontSize, fontWeight, zIndex, breakpoints, sidebarWidth } from '../styles/theme';

export type colorType = typeof colors;
export type zIndexType = typeof zIndex;
export type fontSizeType = typeof fontSize;
export type fontWeightType = typeof fontWeight;
export type breakpoints = typeof breakpoints;
export type sidebarWidth = typeof sidebarWidth;

type ThemeType = {
  fontSize: fontSizeType;
  fontWeight: fontWeightType;
  colors: colorType;
  zIndex: zIndexType;
  breakpoints: breakpoints;
  sidebarWidth: sidebarWidth;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
