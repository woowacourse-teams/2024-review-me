import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router';

import * as S from './styles';

interface TabInfoItem {
  name: string;
  path: string;
}

interface NavigationTabProps {
  tabInfoList: TabInfoItem[];
  tabIndex: number;
}

const NavigationTab = ({ tabInfoList, tabIndex }: NavigationTabProps) => {
  const [currentIndex, setCurrentIndex] = useState(tabIndex);
  const [currentTabWidth, setCurrentTabWidth] = useState(0);
  const [currentTabLeft, setCurrentTabLeft] = useState(0);

  const currentItemRef = useRef<HTMLUListElement>(null);

  const navigate = useNavigate();

  const handleTabClick = (path: string, index: number) => {
    setCurrentIndex(index);
    navigate(path);
  };

  useEffect(() => {
    if (currentItemRef.current) {
      const currentTab = currentItemRef.current.children[currentIndex];
      const { width, left } = currentTab.getBoundingClientRect();
      setCurrentTabWidth(width);
      setCurrentTabLeft(left);
    }
  }, [currentIndex]);

  return (
    <S.NavContainer>
      <S.NavList ref={currentItemRef}>
        {tabInfoList.map((item, index) => (
          <S.NavItem key={index} selected={currentIndex === index}>
            <button onClick={() => handleTabClick(item.path, index)}>{item.name}</button>
          </S.NavItem>
        ))}
      </S.NavList>
      <S.CurrentNavBar width={currentTabWidth} left={currentTabLeft} />
    </S.NavContainer>
  );
};

export default NavigationTab;
