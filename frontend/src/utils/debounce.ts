/* eslint-disable @typescript-eslint/no-explicit-any */
export function debounce<T extends (...args: any[]) => void>(func: T, wait: number): T {
  let timeoutId: ReturnType<typeof setTimeout> | undefined;

  return function (this: any, ...args: any[]) {
    if (timeoutId !== undefined) {
      clearTimeout(timeoutId);
    }

    timeoutId = setTimeout(() => {
      func.apply(this, args);
    }, wait);
  } as T;
}
