import { useCallback, useEffect, useRef } from 'react';

export interface InfiniteScrollProps {
  fetchNextPage: () => void;
  isFetchingNextPage: boolean;
  isLastPage: boolean;
}

const useInfiniteScroll = ({ fetchNextPage, isFetchingNextPage, isLastPage }: InfiniteScrollProps) => {
  const observer = useRef<IntersectionObserver | null>(null);
  const lastElementRef = useRef<HTMLDivElement | null>(null);

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
      if (entries[0].isIntersecting && !isFetchingNextPage && !isLastPage) {
        fetchNextPage();
      }
    },
    [fetchNextPage, isFetchingNextPage, isLastPage],
  );

  useEffect(() => {
    if (observer.current) observer.current.disconnect();
    observer.current = new IntersectionObserver(handleObserver);

    if (lastElementRef.current) {
      observer.current.observe(lastElementRef.current);
    }

    return () => {
      if (observer.current) observer.current.disconnect();
    };
  }, [handleObserver]);

  return lastElementRef;
};

export default useInfiniteScroll;
