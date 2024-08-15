import { useLocation } from 'react-router-dom';

const useBreadcrumbPaths = () => {
  const location = useLocation();

  const breadcrumbPaths = [
    { pageName: '연결 페이지', path: '/' }, // TODO: 연결 페이지 경로 결정되면 수정 필요
  ];

  if (location.pathname === '/user/review-list') {
    breadcrumbPaths.push({ pageName: '목록 페이지', path: location.pathname });
  }

  if (location.pathname === '/user/review-writing') {
    breadcrumbPaths.push({ pageName: '작성 페이지', path: location.pathname });
  }

  if (location.pathname.includes('/user/detailed-review/')) {
    breadcrumbPaths.push(
      { pageName: '목록 페이지', path: '/user/review-list' },
      { pageName: '상세 페이지', path: location.pathname },
    );
  }

  return breadcrumbPaths;
};

export default useBreadcrumbPaths;