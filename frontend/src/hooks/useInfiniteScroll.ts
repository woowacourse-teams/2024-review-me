import { useCallback, useEffect, useRef } from 'react';

export interface InfiniteScrollProps {
  fetchNextPage: () => void;
  isFetchingNextPage: boolean;
  isLastPage: boolean;
}

const useInfiniteScroll = ({ fetchNextPage, isFetchingNextPage, isLastPage }: InfiniteScrollProps) => {
  const observer = useRef<IntersectionObserver | null>(null);

  const lastElementRef = useCallback(
    (node: HTMLElement | null) => {
      if (isFetchingNextPage || isLastPage) return;
      if (observer.current) observer.current.disconnect();

      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && !isLastPage) {
          fetchNextPage();
        }
      });

      if (node) observer.current.observe(node);
    },
    [isFetchingNextPage, fetchNextPage, isLastPage],
  );

  useEffect(() => {
    return () => {
      if (observer.current) observer.current.disconnect();
    };
  }, []);

  return lastElementRef;
};

export default useInfiniteScroll;
