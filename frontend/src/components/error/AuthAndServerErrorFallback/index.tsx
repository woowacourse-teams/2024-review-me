import { FallbackProps } from 'react-error-boundary';
import { useNavigate } from 'react-router';

import { ROUTE } from '@/constants/route';
import { useSearchParamAndQuery } from '@/hooks';

import AuthAndServerErrorSection from '../AuthAndServerErrorSection';

const AuthAndServerErrorFallback = ({ error, resetErrorBoundary }: FallbackProps) => {
  const navigate = useNavigate();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const handleGoReviewZone = () => {
    resetErrorBoundary();
    navigate(`/${ROUTE.reviewZone}/${reviewRequestCode}`);
  };

  return (
    <AuthAndServerErrorSection
      errorMessage={error.message}
      handleGoOtherPage={handleGoReviewZone}
      handleReload={resetErrorBoundary}
    />
  );
};

export default AuthAndServerErrorFallback;
