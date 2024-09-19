import '@emotion/react';

import {
  colors,
  fontSize,
  fontWeight,
  zIndex,
  breakpoint,
  sidebarWidth,
  borderRadius,
  componentHeight,
} from '../styles/theme';

// TODO: export 해서 사용하지 않다면 리팩토링
export type Color = typeof colors;
export type ZIndex = typeof zIndex;
export type FontSize = typeof fontSize;
export type FontWeight = typeof fontWeight;
export type Breakpoint = typeof breakpoint;
export type SidebarWidthStyle = typeof sidebarWidth;
export type BorderRadius = typeof borderRadius;
export type ComponentHeight = typeof componentHeight;

type ThemeType = {
  fontSize: FontSize;
  fontWeight: FontWeight;
  colors: Color;
  zIndex: ZIndex;
  breakpoint: Breakpoint;
  sidebarWidth: SidebarWidthStyle;
  borderRadius: BorderRadius;
  formWidth: string;
  componentHeight: ComponentHeight;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
