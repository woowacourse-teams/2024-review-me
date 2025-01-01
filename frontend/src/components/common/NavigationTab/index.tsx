import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';

import * as S from './styles';

interface TabInfoItem {
  name: string;
  path: string;
  param: string;
}

interface NavigationTabProps {
  tabInfoList: TabInfoItem[];
}

const NavigationTab = ({ tabInfoList }: NavigationTabProps) => {
  const activeTab = sessionStorage.getItem('activeTab');

  const [currentIndex, setCurrentIndex] = useState(Number(activeTab));
  const [currentTabWidth, setCurrentTabWidth] = useState(0);
  const [currentTabLeft, setCurrentTabLeft] = useState(0);

  const location = useLocation();
  const navigate = useNavigate();

  const currentItemRef = useRef<HTMLUListElement>(null);

  // URL의 쿼리 파라미터 값을 읽어, 탭 인덱스 업데이트
  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const tabParam = queryParams.get('tab');
    const tabIndex = tabInfoList.findIndex((item) => item.param === tabParam);

    if (tabIndex >= 0) {
      setCurrentIndex(tabIndex);
      sessionStorage.setItem('activeTab', String(tabIndex));
    }
  }, [location.search]);

  // 탭이 변경될 때마다 현재 탭의 크기와 위치 업데이트
  useEffect(() => {
    if (currentItemRef.current) {
      const currentTab = currentItemRef.current.children[currentIndex];
      const { width, left } = currentTab.getBoundingClientRect();
      setCurrentTabWidth(width);
      setCurrentTabLeft(left);
    }
  }, [currentIndex]);

  const handleTabClick = (path: string, index: number) => {
    setCurrentIndex(index);
    navigate(`${path}?tab=${tabInfoList[index].param}`);
  };

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
