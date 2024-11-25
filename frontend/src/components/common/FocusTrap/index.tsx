import { useEffect, useRef, useState } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

const FocusTrap = ({ children }: EssentialPropsWithChildren) => {
  const [hasAnnounced, setHasAnnounced] = useState(false);
  const focusTrapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const focusableElements = Array.from(
      focusTrapRef.current?.querySelectorAll(
        'a[href], button, textarea, input, select, [tabindex]:not([tabindex="-1"])',
      ) || [],
    ).filter((element) => {
      const el = element as HTMLElement;
      // disabled 상태거나 보이지 않는 요소 제외
      return !el.hasAttribute('disabled') && el.offsetParent !== null;
    });

    const firstElement = focusableElements?.[0] as HTMLElement;
    const lastElement = focusableElements?.[focusableElements.length - 1] as HTMLElement;

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key !== 'Tab') return;

      if (focusableElements?.length === 0) return;

      if (event.shiftKey) {
        if (document.activeElement === firstElement) {
          lastElement.focus();
          event.preventDefault();
        }
      } else {
        if (document.activeElement === lastElement) {
          firstElement.focus();
          event.preventDefault();
        }
      }
    };

    const handleFocusIn = (e: FocusEvent) => {
      if (!focusTrapRef.current?.contains(e.target as Node)) {
        firstElement.focus();
      }
    };

    document.addEventListener('keydown', handleKeyDown);
    document.addEventListener('focusin', handleFocusIn);

    // 모달을 포함하지 않는 body의 자식들에 aria-hidden 적용
    const bodyChildren = document.body.children;
    for (const bodyChildrenElement of bodyChildren) {
      if (!bodyChildrenElement.contains(focusTrapRef.current)) {
        bodyChildrenElement.setAttribute('aria-hidden', 'true');
      }
    }

    // 처음 한 번만 스크린 리더에 안내 문구 제공
    setHasAnnounced(true);
    setTimeout(() => {
      setHasAnnounced(false);
    }, 200);

    return () => {
      document.removeEventListener('keydown', handleKeyDown);
      document.removeEventListener('focusin', handleFocusIn);

      // 모달이 닫힐 때 aria-hidden 해제
      for (const bodyChildrenElement of bodyChildren) {
        bodyChildrenElement.removeAttribute('aria-hidden');
      }
    };
  }, []);

  return (
    <S.FocusTrapContainer ref={focusTrapRef} role="dialog" aria-modal={true}>
      {hasAnnounced && (
        <span aria-live="assertive" className="sr-only">
          모달이 열렸습니다
        </span>
      )}
      {children}
    </S.FocusTrapContainer>
  );
};

export default FocusTrap;
