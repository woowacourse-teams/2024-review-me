import { FallbackProps } from 'react-error-boundary';
import { useNavigate } from 'react-router';

import ErrorSection from '../ErrorSection';

const ErrorFallback = ({ error, resetErrorBoundary }: FallbackProps) => {
  const navigate = useNavigate();
  const handleGoHome = () => {
    resetErrorBoundary();
    navigate('/'); //TODO : 홈 페이지 경로가 결정되면 변경
  };

  return <ErrorSection errorMessage={error.message} handleGoHome={handleGoHome} handleReload={resetErrorBoundary} />;
};

export default ErrorFallback;
