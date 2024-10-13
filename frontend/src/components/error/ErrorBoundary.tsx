import React, { Component, ReactNode } from 'react';

import { ERROR_BOUNDARY_IGNORE_ERROR } from '@/constants';

export interface FallbackProps {
  error: Error;
  resetErrorBoundary: () => void;
}

interface ErrorBoundaryProps {
  fallback: React.ComponentType<FallbackProps>;
  children: ReactNode;
  resetQueryError: () => void;
}

interface ErrorBoundaryState {
  hasError: boolean;
  error: Error | null;
}

class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    // 에러가 발생하면 상태를 업데이트하여 fallback을 표시하게 함
    return { hasError: true, error };
  }

  componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
    // 에러를 로깅하거나 다른 작업을 수행할 수 있음
    console.error('ErrorBoundary caught an error', error, errorInfo);
  }

  resetErrorBoundary = () => {
    this.props.resetQueryError();
    this.setState({ hasError: false, error: null });
  };

  render() {
    const { hasError, error } = this.state;
    const { children, fallback: FallbackComponent } = this.props;

    // 에러가 IgnoredError를 포함하면 children을 그대로 보여줌
    const isHandledError = !error?.message.includes(ERROR_BOUNDARY_IGNORE_ERROR);

    // 에러가 발생했을 때 fallback 컴포넌트로 대체
    if (hasError && error && isHandledError) {
      return <FallbackComponent error={error} resetErrorBoundary={this.resetErrorBoundary} />;
    }
    return children;
  }
}

export default ErrorBoundary;
