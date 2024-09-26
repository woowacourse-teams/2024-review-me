import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  // NOTE: 무한스크롤 코드 일단 주석 처리
  // const { data, fetchNextPage, hasNextPage, isLoading, error } = useGetReviewList();
  // const observer = useRef<IntersectionObserver | null>(null);

  // const lastReviewElementRef = useCallback(
  //   (node: HTMLElement | null) => {
  //     if (isLoading) return <LoadingPage />;
  //     if (observer.current) observer.current.disconnect();

  //     observer.current = new IntersectionObserver((entries) => {
  //       if (entries[0].isIntersecting && hasNextPage) {
  //         fetchNextPage();
  //       }
  //     });

  //     if (node) observer.current.observe(node);
  //   },
  //   [isLoading, fetchNextPage, hasNextPage],
  // );

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <PageContents />
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
