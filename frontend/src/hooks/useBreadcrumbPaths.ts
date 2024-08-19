import { useLocation } from 'react-router-dom';

import { Path } from '@/components/common/Breadcrumb';
import { ROUTE } from '@/constants/route';

const useBreadcrumbPaths = () => {
  const { pathname } = useLocation();

  const breadcrumbPathList: Path[] = [
    { pageName: '연결 페이지', path: ROUTE.home }, // TODO: 연결 페이지 경로 결정되면 수정 필요
  ];

  if (pathname === `/${ROUTE.reviewList}`) {
    breadcrumbPathList.push({ pageName: '목록 페이지', path: pathname });
  }

  if (pathname.includes(`/${ROUTE.reviewWriting}/`)) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: pathname });
  }

  if (pathname === `/${ROUTE.reviewWritingComplete}`) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: -1 }, { pageName: '작성 완료 페이지', path: pathname });
  }

  if (pathname.includes(ROUTE.detailedReview)) {
    breadcrumbPathList.push(
      { pageName: '목록 페이지', path: ROUTE.reviewList },
      { pageName: '상세 페이지', path: pathname },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
