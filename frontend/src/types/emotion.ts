import '@emotion/react';
import theme, { colors, fontSize, fontWeight, zIndex } from '../styles/theme';

export type colorType = typeof colors;
export type zIndexType = typeof zIndex;
export type fontSizeType = typeof fontSize;
export type fontWeightType = typeof fontWeight;

type ThemeType = {
  fontSize: fontSizeType;
  fontWeight: fontWeightType;
  colors: colorType;
  zIndex: zIndexType;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
