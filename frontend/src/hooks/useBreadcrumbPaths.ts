import { useLocation } from 'react-router-dom';

import { Path } from '@/components/common/Breadcrumb';
import { ROUTE_PARAM } from '@/constants';
import { ROUTE } from '@/constants/route';

import useSearchParamAndQuery from './useSearchParamAndQuery';

const useBreadcrumbPaths = () => {
  const { pathname } = useLocation();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const { param: reviewId } = useSearchParamAndQuery({
    paramKey: ROUTE_PARAM.reviewId,
  });

  const breadcrumbPathList: Path[] = [{ pageName: '연결 페이지', path: `${ROUTE.reviewZone}/${reviewRequestCode}` }];

  if (pathname === `/${ROUTE.reviewList}/${reviewRequestCode}`) {
    breadcrumbPathList.push({ pageName: '목록 페이지', path: `${ROUTE.reviewList}/${reviewRequestCode}` });
  }

  if (pathname.includes(`/${ROUTE.reviewWriting}/`)) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: pathname });
  }

  if (pathname === `/${ROUTE.reviewWritingComplete}`) {
    breadcrumbPathList.push({ pageName: '작성 페이지', path: -1 }, { pageName: '작성 완료 페이지', path: pathname });
  }

  if (pathname.includes(ROUTE.detailedReview)) {
    breadcrumbPathList.push(
      { pageName: '목록 페이지', path: `${ROUTE.reviewList}/${reviewRequestCode}` },
      {
        pageName: '상세 페이지',
        path: `${ROUTE.detailedReview}/${reviewRequestCode}/${reviewId}`,
      },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
