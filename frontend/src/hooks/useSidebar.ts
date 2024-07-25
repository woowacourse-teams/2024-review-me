import { useState } from 'react';

const useSidebar = () => {
  const CLOSE_TIME = 1000;
  const OPEN_TIME = 0.5;

  const [isSidebarModalOpen, setIsSidebarModalOpen] = useState(false);
  const [isSidebarHidden, setIsSidebarHidden] = useState(true);

  const closeSidebar = () => {
    setIsSidebarHidden(true);
    setTimeout(() => {
      setIsSidebarModalOpen(false);
    }, CLOSE_TIME);
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
