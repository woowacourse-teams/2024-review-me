import { useEffect } from 'react';

export type NullableElement = HTMLElement | Element | null | undefined;

const useExistentElement = (element: NullableElement, elementName: string) => {
  useEffect(() => {
    const isExistentElement = element !== null && element !== undefined;
    const isHTMLElementOrElement = element instanceof HTMLElement || element instanceof Element;

    if (!isExistentElement || !isHTMLElementOrElement) {
      console.error(`${elementName}을/를 찾을 수 없습니다.`);
    }
  }, [element, elementName]);
};

export default useExistentElement;
