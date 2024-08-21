// TODO: 추후 any를 제거할 수 있다면 제거할 것!

/**
 * 디바운스를 수행하는 함수입니다.
 *
 * @param func - 디바운스 대상 함수
 * @param wait - 딜레이 시간(대기 시간)
 * @returns - 대상 함수에 디바운스를 적용한 함수
 *
 * @example
 * ```
 * const saveInput = debounce((input) => {
 *   console.log('Saving data:', input);
 * }, 300);
 *
 * // Usage
 * saveInput('first call');
 * saveInput('second call');
 * // 300ms 후에 마지막 호출만 실행됨
 * ```
 */

/* eslint-disable @typescript-eslint/no-explicit-any */
export function debounce<T extends (...args: any[]) => void>(func: T, wait: number): T {
  let timeoutId: ReturnType<typeof setTimeout> | null;

  return function (this: any, ...args: any[]) {
    if (timeoutId) clearTimeout(timeoutId);

    timeoutId = setTimeout(() => {
      func.apply(this, args);
    }, wait);
  } as T;
}
