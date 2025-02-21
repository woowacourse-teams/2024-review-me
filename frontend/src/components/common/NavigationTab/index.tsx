import { useLocation } from 'react-router';

import NavItem from './NavItem';
import * as S from './styles';
export interface Tab {
  label: '리뷰 링크 관리' | '작성한 리뷰 확인';
  activePathList: string[];
  handleTabClick: () => void;
}

interface NavigationTabProps {
  tabList: Tab[];
}

const NavigationTab = ({ tabList }: NavigationTabProps) => {
  const { pathname } = useLocation();

  const isActiveTab = (activePaths: string[]) => activePaths.some((activePath) => pathname.includes(activePath));

  return (
    <S.NavContainer>
      <S.NavList>
        {tabList.map((tab) => (
          <NavItem
            key={tab.label}
            label={tab.label}
            $isActiveTab={isActiveTab(tab.activePathList)}
            onClick={tab.handleTabClick}
          />
        ))}
      </S.NavList>
    </S.NavContainer>
  );
};

export default NavigationTab;
