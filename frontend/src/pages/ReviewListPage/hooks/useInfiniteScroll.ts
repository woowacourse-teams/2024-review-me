import { useCallback, useRef } from 'react';

export interface InfiniteScrollProps {
  fetchNextPage: () => void;
  isLoading: boolean;
  isLastPage: boolean;
}

const useInfiniteScroll = ({ fetchNextPage, isLoading, isLastPage }: InfiniteScrollProps) => {
  const observer = useRef<IntersectionObserver | null>(null);

  const lastElementRef = useCallback(
    (node: HTMLElement | null) => {
      if (isLoading) return;
      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && !isLastPage) {
          fetchNextPage();
        }
      });

      if (node) observer.current.observe(node);
    },
    [isLoading, fetchNextPage, isLastPage],
  );

  return lastElementRef;
};

export default useInfiniteScroll;
