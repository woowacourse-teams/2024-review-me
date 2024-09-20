type NullableElement = Element | HTMLElement | null | undefined;

const isExistentElement = (element: NullableElement, elementName: string): boolean => {
  const isExistentElement = element !== null && element !== undefined;
  const isHTMLElementOrElement = element instanceof HTMLElement || element instanceof Element;

  if (!isExistentElement) {
    console.error(`${elementName}을/를 찾을 수 없어요`);
    return false;
  }

  if (!isHTMLElementOrElement) {
    console.error(`${elementName}은/는 Element 이하의 타입이 아니에요`);
    return false;
  }

  return true;
};

export default isExistentElement;
