import { useEffect, useRef, useState } from 'react';

import { DropdownItem } from '@/components/common/Dropdown';

interface UseDropdownProps {
  handleSelect: (option: DropdownItem) => void;
}

const useDropdown = ({ handleSelect }: UseDropdownProps) => {
  const [isOpened, setIsOpened] = useState(false);

  const dropdownRef = useRef<HTMLDivElement>(null);

  const handleDropdownButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  const handleOptionClick = (option: DropdownItem) => {
    handleSelect(option);
    setIsOpened(false);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
      setIsOpened(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [dropdownRef]);

  return { isOpened, handleDropdownButtonClick, handleOptionClick, dropdownRef };
};

export default useDropdown;
