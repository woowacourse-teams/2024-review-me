import { useNavigate } from 'react-router';

import { Tab } from '@/components/common/NavigationTab';
import { ROUTE } from '@/constants';

const useNavigationTabs = () => {
  const navigate = useNavigate();

  const navigationTabList: Tab[] = [
    {
      label: '리뷰 링크 관리',
      // "리뷰 링크 관리" 탭이 활성화 되어야 하는 페이지 목록
      activePathList: [ROUTE.reviewLinks, ROUTE.reviewList, ROUTE.reviewCollection, ROUTE.detailedReview],
      handleTabClick: () => navigate(ROUTE.reviewLinks),
    },
    {
      label: '작성한 리뷰 확인',
      // "작성한 리뷰 확인" 탭이 활성화 되어야 하는 페이지 목록
      activePathList: [ROUTE.writtenReview, ROUTE.reviewWriting, ROUTE.reviewWritingComplete],
      handleTabClick: () => navigate(ROUTE.writtenReview),
    },
  ];

  return navigationTabList;
};

export default useNavigationTabs;
