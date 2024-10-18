/**
 * 터치 가능 장치인지 확인
 */
const isTouchDevice = () => {
  return 'ontouchstart' in window || navigator.maxTouchPoints > 0 || window.matchMedia('(pointer: coarse)').matches;
};

export default isTouchDevice;
