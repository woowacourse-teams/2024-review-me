import { useLocation } from 'react-router-dom';

import { Path } from '@/components/common/Breadcrumb';

const useBreadcrumbPaths = () => {
  const location = useLocation();

  const breadcrumbPathList: Path[] = [
    { pageName: '연결 페이지', path: '/' }, // TODO: 연결 페이지 경로 결정되면 수정 필요
  ];

  if (location.pathname === '/user/review-list') {
    breadcrumbPathList.push({ pageName: '목록 페이지', path: location.pathname });
  }

  if (location.pathname.includes('/user/review-writing/')) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: location.pathname });
  }

  if (location.pathname === '/user/review-writing-complete') {
    breadcrumbPathList.push(
      { pageName: '작성 페이지', path: -1 },
      { pageName: '작성 완료 페이지', path: location.pathname },
    );
  }

  if (location.pathname.includes('/user/detailed-review/')) {
    breadcrumbPathList.push(
      { pageName: '목록 페이지', path: '/user/review-list' },
      { pageName: '상세 페이지', path: location.pathname },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
