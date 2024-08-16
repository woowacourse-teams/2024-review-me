import { useLocation } from 'react-router-dom';

import { Path } from '@/components/common/Breadcrumb';
import { ROUTES } from '@/constants/routes';

const useBreadcrumbPaths = () => {
  const { pathname } = useLocation();

  const breadcrumbPathList: Path[] = [
    { pageName: '연결 페이지', path: ROUTES.HOME }, // TODO: 연결 페이지 경로 결정되면 수정 필요
  ];

  if (pathname === ROUTES.REVIEW_LIST) {
    breadcrumbPathList.push({ pageName: '목록 페이지', path: pathname });
  }

  if (pathname.includes(`${ROUTES.REVIEW_WRITING}/`)) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: pathname });
  }

  if (pathname === ROUTES.REVIEW_WRITING_COMPLETE) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: -1 }, { pageName: '작성 완료 페이지', path: pathname });
  }

  if (pathname.includes(ROUTES.DETAILED_REVIEW)) {
    breadcrumbPathList.push(
      { pageName: '목록 페이지', path: ROUTES.REVIEW_LIST },
      { pageName: '상세 페이지', path: pathname },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
