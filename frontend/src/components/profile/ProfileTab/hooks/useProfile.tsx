import { useEffect, useRef, useState } from 'react';

const useProfile = () => {
  const [isOpened, setIsOpened] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);

  const handleClickOutside = (event: MouseEvent) => {
    if (containerRef.current && !containerRef.current.contains(event.target as Node)) {
      setIsOpened(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [containerRef]);

  const handleContainerClick = () => {
    setIsOpened((prev) => !prev);
  };

  return { isOpened, containerRef, handleContainerClick };
};

export default useProfile;
