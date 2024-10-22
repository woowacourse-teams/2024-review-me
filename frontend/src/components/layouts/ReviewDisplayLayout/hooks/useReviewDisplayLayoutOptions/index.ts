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

  const navigateReviewListPage = () => {
    trackEventInAmplitude(COLLECTION_LIST_SWITCH_EVENT_NAME.list);
    navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`);
  };

  const navigateReviewCollectionPage = () => {
    trackEventInAmplitude(COLLECTION_LIST_SWITCH_EVENT_NAME.collection);
    navigate(`/${ROUTE.reviewCollection}/${reviewRequestCode}`);
  };

  const reviewDisplayLayoutOptions: OptionSwitchOption[] = [
    {
      label: '목록보기',
      isChecked: !isReviewCollection,
      handleOptionClick: navigateReviewListPage,
    },
    {
      label: '모아보기',
      isChecked: isReviewCollection,
      handleOptionClick: navigateReviewCollectionPage,
    },
  ];

  return [...reviewDisplayLayoutOptions];
};

export default useReviewDisplayLayoutOptions;
