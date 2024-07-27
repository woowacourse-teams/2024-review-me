import { useState } from 'react';

const useSidebar = () => {
  const OPEN_TIME = 0.5;

  const [isSidebarModalOpen, setIsSidebarModalOpen] = useState(false);
  const [isSidebarHidden, setIsSidebarHidden] = useState(true);

  const closeSidebar = () => {
    setIsSidebarModalOpen(false);
    setIsSidebarHidden(true);
  };

  const openSidebar = () => {
    setIsSidebarModalOpen(true);

    setTimeout(() => {
      setIsSidebarHidden(false);
    }, OPEN_TIME);
  };
  return {
    isSidebarHidden,
    isSidebarModalOpen,
    closeSidebar,
    openSidebar,
  };
};

export default useSidebar;
