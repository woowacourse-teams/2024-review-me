import { useLocation, useNavigate } from 'react-router';

import { ROUTE } from '@/constants';

const useNavigationTabs = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const navigateReviewLinkManagementPage = () => {
    navigate(`/${ROUTE.reviewLinks}`);
  };

  const navigateWrittenReviewConfirmPage = () => {
    navigate(`/${ROUTE.writtenReview}`);
  };

  const tabList = [
    {
      label: '리뷰 링크 관리',
      path: `/${ROUTE.reviewLinks}`,
      handleTabClick: navigateReviewLinkManagementPage,
    },
    {
      label: '작성한 리뷰 확인',
      path: `/${ROUTE.writtenReview}`,
      handleTabClick: navigateWrittenReviewConfirmPage,
    },
  ];

  const currentTabIndex = tabList.findIndex((tab) => tab.path === pathname);

  return { currentTabIndex, tabList };
};

export default useNavigationTabs;
