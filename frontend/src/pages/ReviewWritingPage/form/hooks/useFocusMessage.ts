import { useRef, useEffect } from 'react';

interface useMessageFocusProps {
  isMessageShown: boolean;
}

const useFocusMessage = <T extends HTMLElement = HTMLElement>({ isMessageShown }: useMessageFocusProps) => {
  const messageRef = useRef<T | null>(null);

  useEffect(() => {
    if (isMessageShown && messageRef.current) {
      messageRef.current.focus();
    }
  }, [isMessageShown]);

  return {
    messageRef,
  };
};

export default useFocusMessage;
