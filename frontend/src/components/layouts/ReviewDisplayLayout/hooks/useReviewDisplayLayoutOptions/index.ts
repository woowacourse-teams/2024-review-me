import { useLocation, useNavigate } from 'react-router';

import { OptionSwitchOption } from '@/components/common/OptionSwitch';
import { ROUTE } from '@/constants/route';
import { useSearchParamAndQuery } from '@/hooks';

const useReviewDisplayLayoutOptions = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const isReviewCollection = pathname.includes(ROUTE.reviewCollection);

  const reviewDisplayLayoutOptions: OptionSwitchOption[] = [
    {
      label: '목록보기',
      isChecked: !isReviewCollection,
      handleOptionClick: () => navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`),
    },
    {
      label: '모아보기',
      isChecked: isReviewCollection,
      handleOptionClick: () => navigate(`/${ROUTE.reviewCollection}/${reviewRequestCode}`),
    },
  ];

  return [...reviewDisplayLayoutOptions];
};

export default useReviewDisplayLayoutOptions;
