import '@emotion/react';

import {
  colors,
  fontSize,
  fontWeight,
  zIndex,
  scrollbarWidth,
  borderRadius,
  componentHeight,
  confirmModalSize,
  contentModalSize,
  breakpointsWidth,
  breadcrumbSize,
} from '../styles/theme';

export type Color = typeof colors;
export type ZIndex = typeof zIndex;
export type FontSize = typeof fontSize;
export type FontWeight = typeof fontWeight;
export type ScrollbarWidthStyle = typeof scrollbarWidth;
export type BorderRadius = typeof borderRadius;
export type ComponentHeight = typeof componentHeight;
export type ConfirmModalSize = typeof confirmModalSize;
export type ContentModalSize = typeof contentModalSize;
export type BreakpointsWidth = typeof breakpointsWidth;
export type BreadcrumbSize = typeof breadcrumbSize;

type ThemeType = {
  fontSize: FontSize;
  fontWeight: FontWeight;
  colors: Color;
  zIndex: ZIndex;
  scrollbarWidth: ScrollbarWidthStyle;
  borderRadius: BorderRadius;
  formWidth: string;
  componentHeight: ComponentHeight;
  confirmModalSize: ConfirmModalSize;
  contentModalSize: ContentModalSize;
  breakpointsWidth: BreakpointsWidth;
  breadcrumbSize: BreadcrumbSize;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
