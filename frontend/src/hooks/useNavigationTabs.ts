import { useLocation, useNavigate } from 'react-router';

const useNavigationTabs = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const navigateReviewLinkManagementPage = () => {
    navigate('/user/review-link-management');
  };

  const navigateWrittenReviewConfirmPage = () => {
    navigate('/user/written-review-confirm');
  };

  const tabList = [
    {
      label: '리뷰 링크 관리',
      path: '/user/review-link-management',
      handleTabClick: navigateReviewLinkManagementPage,
    },
    {
      label: '작성한 리뷰 확인',
      path: '/user/written-review-confirm',
      handleTabClick: navigateWrittenReviewConfirmPage,
    },
  ];

  const currentTabIndex = tabList.findIndex((tab) => tab.path === pathname);

  return { currentTabIndex, tabList };
};

export default useNavigationTabs;
