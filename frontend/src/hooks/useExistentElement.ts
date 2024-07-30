import { useEffect, RefObject } from 'react';

const useExistentElement = <T extends HTMLElement>(ref: RefObject<T>, elementName: string) => {
  useEffect(() => {
    if (!ref.current) {
      console.error(`${elementName}을/를 찾을 수 없습니다.`);
    }
  }, [ref, elementName]);
};

export default useExistentElement;
