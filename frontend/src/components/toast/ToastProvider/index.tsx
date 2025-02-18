import { createContext, useState } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import Toast, { ToastProps } from '../Toast';

interface ToastStateProps extends ToastProps {
  isOpen: boolean;
}

const DEFAULT_TOAST_STATE: ToastStateProps = {
  type: 'success',
  message: '',
  durationMS: 5000,
  position: 'bottom',
  isOpen: false,
};

interface ToastContextType {
  showToast: ({ type, message, durationMS, position }: ToastProps) => void;
  hideToast: () => void;
}

export const ToastContext = createContext<ToastContextType | null>(null);

const ToastProvider = ({ children }: EssentialPropsWithChildren) => {
  const [toast, setToast] = useState<ToastStateProps>(DEFAULT_TOAST_STATE);

  const showToast = ({ type = 'success', message, durationMS = 5000, position = 'bottom' }: ToastProps) => {
    setToast({ type, message, durationMS, position, isOpen: true });

    const timer = setTimeout(hideToast, durationMS);
    return () => clearTimeout(timer);
  };

  const hideToast = () => {
    setToast((prev) => ({ ...prev, isOpen: false }));
  };

  return (
    <ToastContext.Provider value={{ showToast, hideToast }}>
      {children}
      {toast.isOpen && (
        <Toast type={toast.type} message={toast.message} durationMS={toast.durationMS} position={toast.position} />
      )}
    </ToastContext.Provider>
  );
};

export default ToastProvider;
