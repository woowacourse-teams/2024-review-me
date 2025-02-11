import { useNavigate } from 'react-router';

import { ROUTE } from '@/constants';

interface UseNavigationTabsProps {
  selectedTab: string;
}

const useNavigationTabs = ({ selectedTab }: UseNavigationTabsProps) => {
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
      handleTabClick: navigateReviewLinkManagementPage,
    },
    {
      label: '작성한 리뷰 확인',
      handleTabClick: navigateWrittenReviewConfirmPage,
    },
  ];

  const currentTabIndex = tabList.findIndex((tab) => tab.label === selectedTab);

  return { currentTabIndex, tabList };
};

export default useNavigationTabs;
