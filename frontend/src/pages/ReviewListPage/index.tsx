import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  const { groupAccessCode } = useGroupAccessCode();

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
    <>
      {groupAccessCode ? (
        <ErrorSuspenseContainer>
          <PageContents groupAccessCode={groupAccessCode} />
        </ErrorSuspenseContainer>
      ) : (
        <LoginRedirectModal />
      )}
    </>
  );
};

export default ReviewListPage;
