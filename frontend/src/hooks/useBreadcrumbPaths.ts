import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { Path } from '@/components/common/Breadcrumb';
import { ROUTE } from '@/constants/route';
import { reviewRequestCodeAtom } from '@/recoil';

const useBreadcrumbPaths = () => {
  const { pathname } = useLocation();
  const storedReviewRequestCode = useRecoilValue(reviewRequestCodeAtom);

  const breadcrumbPathList: Path[] = [
    { pageName: '연결 페이지', path: `${ROUTE.reviewZone}/${storedReviewRequestCode}` },
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
