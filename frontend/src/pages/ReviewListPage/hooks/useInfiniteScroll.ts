import { useCallback, useRef } from 'react';

export interface InfiniteScrollProps {
  fetchNextPage: () => void;
  hasNextPage: boolean;
  isLoading: boolean;
}

const useInfiniteScroll = ({ fetchNextPage, hasNextPage, isLoading }: InfiniteScrollProps) => {
  const observer = useRef<IntersectionObserver | null>(null);

  const lastElementRef = useCallback(
    (node: HTMLElement | null) => {
      if (isLoading) return;
      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNextPage) {
          fetchNextPage();
        }
      });

      if (node) observer.current.observe(node);
    },
    [isLoading, fetchNextPage, hasNextPage],
  );

  return lastElementRef;
};

export default useInfiniteScroll;
