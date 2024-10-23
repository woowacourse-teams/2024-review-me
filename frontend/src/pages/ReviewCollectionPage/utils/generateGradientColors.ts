const R_SHIFT = 16;
const G_SHIFT = 8;
const RGB_MAX_VALUE = 255;

// Hex 색상을 RGB로 변환하는 함수
const hexToRGB = (hex: string) => {
  const bigint = parseInt(hex.slice(1), 16);
  const r = bigint >> R_SHIFT;
  const g = (bigint >> G_SHIFT) & RGB_MAX_VALUE;
  const b = bigint & RGB_MAX_VALUE;

  return [r, g, b];
};

// RGB 색상을 Hex로 변환하는 함수
const rgbToHex = (r: number, g: number, b: number) => {
  return `#${((1 << 24) + (r << R_SHIFT) + (g << G_SHIFT) + b).toString(16).slice(1).toUpperCase()}`;
};

// 두 색상 사이의 색상을 계산하는 함수
const interpolateColor = (start: number[], end: number[], factor: number) => {
  const result = start.map((startValue, index) => Math.round(startValue + factor * (end[index] - startValue)));
  return result;
};

interface GradientColorProps {
  length: number;
  startHex: string;
  endHex: string;
}

// reviewVotes 길이에 따라 색상 배열을 생성하는 함수
const generateGradientColors = ({ length, startHex, endHex }: GradientColorProps) => {
  const startColor = hexToRGB(startHex);
  const endColor = hexToRGB(endHex);
  const colors = [];

  for (let i = 0; i < length; i++) {
    const factor = i / (length - 1);
    const color = interpolateColor(startColor, endColor, factor);
    colors.push(rgbToHex(color[0], color[1], color[2]));
  }

  return colors;
};

export default generateGradientColors;
