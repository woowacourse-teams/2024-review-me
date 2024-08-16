import { useLocation } from 'react-router-dom';

import { Path } from '@/components/common/Breadcrumb';
import { ROUTES } from '@/constants/routes';

const useBreadcrumbPaths = () => {
  const { pathname } = useLocation();

  const breadcrumbPathList: Path[] = [
    { pageName: '연결 페이지', path: ROUTES.home }, // TODO: 연결 페이지 경로 결정되면 수정 필요
  ];

  if (pathname === `/${ROUTES.review_list}`) {
    breadcrumbPathList.push({ pageName: '목록 페이지', path: pathname });
  }

  if (pathname.includes(`/${ROUTES.review_writing}/`)) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: pathname });
  }

  if (pathname === `/${ROUTES.review_writing_complete}`) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: -1 }, { pageName: '작성 완료 페이지', path: pathname });
  }

  if (pathname.includes(ROUTES.detailed_review)) {
    breadcrumbPathList.push(
      { pageName: '목록 페이지', path: ROUTES.review_list },
      { pageName: '상세 페이지', path: pathname },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
