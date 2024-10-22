import { useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router';

import { OptionSwitchOption } from '@/components/common/OptionSwitch';
import { COLLECTION_LIST_SWITCH_EVENT_NAME } from '@/constants';
import { ROUTE } from '@/constants/route';
import { useSearchParamAndQuery } from '@/hooks';
import { trackEventInAmplitude } from '@/utils';

const useReviewDisplayLayoutOptions = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const isReviewCollection = pathname.includes(ROUTE.reviewCollection);

  const navigatePage = useCallback(() => {
    // 리뷰 리스트로 이동
    if (isReviewCollection) {
      trackEventInAmplitude(COLLECTION_LIST_SWITCH_EVENT_NAME.list);
      navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
      return;
    }
    // 리뷰 모아보기로 이동
    trackEventInAmplitude(COLLECTION_LIST_SWITCH_EVENT_NAME.collection);
    navigate(`/${ROUTE.reviewCollection}/${reviewRequestCode}`);
  }, [isReviewCollection]);

  const reviewDisplayLayoutOptions: OptionSwitchOption[] = [
    {
      label: '목록보기',
      isChecked: !isReviewCollection,
      handleOptionClick: navigatePage,
    },
    {
      label: '모아보기',
      isChecked: isReviewCollection,
      handleOptionClick: navigatePage,
    },
  ];

  return [...reviewDisplayLayoutOptions];
};

export default useReviewDisplayLayoutOptions;
