import { useLocation } from 'react-router';

import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import Breadcrumb from '@/components/common/Breadcrumb';
import { useGroupAccessCode } from '@/hooks';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  const location = useLocation();
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

  const paths = [
    { pageName: '연결 페이지', path: '/' }, // TODO: 연결 페이지 경로 결정되면 변경
    { pageName: '목록 페이지', path: location.pathname },
  ];

  return (
    <>
      {groupAccessCode ? (
        <ErrorSuspenseContainer>
          <Breadcrumb paths={paths} />
          <PageContents groupAccessCode={groupAccessCode} />
        </ErrorSuspenseContainer>
      ) : (
        <LoginRedirectModal />
      )}
    </>
  );
};

export default ReviewListPage;
