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

  const breadcrumbPathList: Path[] = [{ pageName: '리뷰 연결', path: `${ROUTE.reviewZone}/${reviewRequestCode}` }];

  if (pathname === `/${ROUTE.reviewList}/${reviewRequestCode}`) {
    breadcrumbPathList.push({ pageName: '리뷰 목록', path: `${ROUTE.reviewList}/${reviewRequestCode}` });
  }

  if (pathname === `/${ROUTE.reviewCollection}/${reviewRequestCode}`) {
    breadcrumbPathList.push({ pageName: '리뷰 모아보기', path: `${ROUTE.reviewCollection}/${reviewRequestCode}` });
  }

  if (pathname.includes(`/${ROUTE.reviewWriting}/`)) {
    breadcrumbPathList.push({ pageName: '리뷰 작성', path: pathname });
  }

  if (pathname.includes(`/${ROUTE.reviewWritingComplete}`)) {
    breadcrumbPathList.push({ pageName: '리뷰 작성', path: -1 }, { pageName: '리뷰 작성 완료 페이지', path: pathname });
  }

  if (pathname.includes(ROUTE.detailedReview)) {
    breadcrumbPathList.push(
      { pageName: '리뷰 목록', path: `${ROUTE.reviewList}/${reviewRequestCode}` },
      {
        pageName: '리뷰 상세',
        path: `${ROUTE.detailedReview}/${reviewRequestCode}/${reviewId}`,
      },
    );
  }

  return breadcrumbPathList;
};

export default useBreadcrumbPaths;
