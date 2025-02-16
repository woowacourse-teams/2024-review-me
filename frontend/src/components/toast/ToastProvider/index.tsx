import { createContext, useContext, useState } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import Toast, { ToastProps } from '../Toast';

interface ToastStateProps extends ToastProps {
  isOpen: boolean;
}

const defaultToastState: ToastStateProps = {
  type: 'success',
  message: '',
  duration: 5,
  position: 'bottom',
  isOpen: false,
};

interface ToastContextType {
  showToast: ({ type, message, duration, position }: ToastProps) => void;
  hideToast: () => void;
}

export const ToastContext = createContext<ToastContextType | null>(null);

export const useToastContext = () => {
  const value = useContext(ToastContext);

  if (!value) throw new Error('컨텍스트가 존재하지 않아요.');

  return value;
};

const ToastProvider = ({ children }: EssentialPropsWithChildren) => {
  const [toast, setToast] = useState<ToastStateProps>(defaultToastState);

  const showToast = ({ type = 'success', message, duration = 5, position = 'bottom' }: ToastProps) => {
    setToast({ type, message, duration, position, isOpen: true });

    const timer = setTimeout(hideToast, duration * 1000);
    return () => clearTimeout(timer);
  };

  const hideToast = () => {
    setToast((prev) => ({ ...prev, isOpen: false }));
  };

  return (
    <ToastContext.Provider value={{ showToast, hideToast }}>
      {children}
      {toast.isOpen && (
        <Toast type={toast.type} message={toast.message} duration={toast.duration} position={toast.position} />
      )}
    </ToastContext.Provider>
  );
};

export default ToastProvider;
