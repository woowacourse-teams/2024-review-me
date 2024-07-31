type NullableElement = Element | HTMLElement | null | undefined;

export const isExistentElement = (element: NullableElement, elementName: string): boolean => {
  const isExistentElement = element !== null && element !== undefined;
  const isHTMLElementOrElement = element instanceof HTMLElement || element instanceof Element;

  if (!isExistentElement) {
    console.error(`${elementName}을/를 찾을 수 없습니다.`);
    return false;
  }

  if (!isHTMLElementOrElement) {
    console.error(`${elementName}은/는 Element 이하의 타입이 아닙니다.`);
    return false;
  }

  return true;
};
