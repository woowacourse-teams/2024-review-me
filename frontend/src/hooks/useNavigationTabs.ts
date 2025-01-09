import { useLocation, useNavigate } from 'react-router';

const useNavigationTabs = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  // TODO: '리뷰 링크 확인', '작성한 리뷰 확인' 페이지 URL 경로 확정되면 변경
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
