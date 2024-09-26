import { useNavigate } from 'react-router';

import { PageLayout, ErrorSection } from '@/components';
import { ROUTE_ERROR_MESSAGE } from '@/constants';

const ErrorPage = () => {
  const navigate = useNavigate();

  const handleReload = () => {
    navigate(0);
  };

  const handleGoHome = () => {
    navigate('/');
  };

  return (
    <PageLayout isNeedBreadCrumb={false}>
      <ErrorSection errorMessage={ROUTE_ERROR_MESSAGE} handleReload={handleReload} handleGoOtherPage={handleGoHome} />
    </PageLayout>
  );
};

export default ErrorPage;
