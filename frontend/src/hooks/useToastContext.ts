import { useContext } from 'react';

import { ToastContext } from '@/components/toast/ToastProvider';

const useToastContext = () => {
  const value = useContext(ToastContext);

  if (!value) throw new Error('컨텍스트가 존재하지 않아요.');

  return value;
};

export default useToastContext;
