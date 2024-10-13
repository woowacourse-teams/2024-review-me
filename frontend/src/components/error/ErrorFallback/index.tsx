import { useNavigate } from 'react-router';

import { FallbackProps } from '../ErrorBoundary';
import ErrorSection from '../ErrorSection';

const ErrorFallback = ({ error, resetErrorBoundary }: FallbackProps) => {
  const navigate = useNavigate();
  const handleGoHome = () => {
    resetErrorBoundary();
    navigate('/');
  };

  return (
    <ErrorSection errorMessage={error.message} handleGoOtherPage={handleGoHome} handleReload={resetErrorBoundary} />
  );
};

export default ErrorFallback;
