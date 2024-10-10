// Hex 색상을 RGB로 변환하는 함수
const hexToRGB = (hex: string) => {
  const bigint = parseInt(hex.slice(1), 16);
  return [bigint >> 16, (bigint >> 8) & 255, bigint & 255];
};

// RGB 색상을 Hex로 변환하는 함수
const rgbToHex = (r: number, g: number, b: number) => {
  return `#${((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1).toUpperCase()}`;
};

// 두 색상 사이의 색상을 계산하는 함수
const interpolateColor = (start: number[], end: number[], factor: number) => {
  const result = start.map((startValue, index) => Math.round(startValue + factor * (end[index] - startValue)));
  return result;
};

// reviewVotes 길이에 따라 색상 배열을 생성하는 함수
const generateGradientColors = (length: number, startHex: string, endHex: string) => {
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
