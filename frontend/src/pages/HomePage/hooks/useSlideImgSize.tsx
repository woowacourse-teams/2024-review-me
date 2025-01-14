import { useEffect, useState } from 'react';

import { debounce } from '@/utils';

const DEBOUNCE_TIME = 300;

interface UseSlideImgSizeProps {
  slideRef: React.RefObject<HTMLDivElement>;
}
const useSlideImgSize = ({ slideRef }: UseSlideImgSizeProps) => {
  interface ImgSize {
    width: string;
    height: string;
  }
  const [imgSize, setImgSize] = useState<ImgSize>({ width: '', height: '' });

  const updateImgSize = () => {
    if (!slideRef.current) return;

    const slideDomRect = slideRef.current.getBoundingClientRect();
    const width = Math.ceil(slideDomRect.width * 0.8 * 0.1);
    const height = width * 0.61;

    setImgSize({ width: `${width}rem`, height: `${height}rem` });
  };

  const debouncedUpdateImgSize = debounce(updateImgSize, DEBOUNCE_TIME);

  useEffect(() => {
    updateImgSize();

    document.addEventListener('resize', debouncedUpdateImgSize);

    return () => {
      document.removeEventListener('resize', debouncedUpdateImgSize);
    };
  }, [slideRef]);

  return { imgSize };
};

export default useSlideImgSize;
