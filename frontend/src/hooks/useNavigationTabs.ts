import { useNavigate } from 'react-router';

import { Tab } from '@/components/common/NavigationTab';
import { ROUTE } from '@/constants';

const useNavigationTabs = () => {
  const navigate = useNavigate();

  const navigationTabList: Tab[] = [
    {
      label: '리뷰 링크 관리',
      activePath: ROUTE.reviewLinks,
      handleTabClick: () => navigate(ROUTE.reviewLinks),
    },
    {
      label: '작성한 리뷰 확인',
      activePath: ROUTE.writtenReview,
      handleTabClick: () => navigate(ROUTE.writtenReview),
    },
  ];

  return navigationTabList;
};

export default useNavigationTabs;
