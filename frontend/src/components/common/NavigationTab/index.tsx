import { useLocation } from 'react-router';

import NavItem from './NavItem';
import * as S from './styles';
export interface Tab {
  label: string;
  activePath: string;
  handleTabClick: () => void;
}

interface NavigationTabProps {
  tabList: Tab[];
}

const NavigationTab = ({ tabList }: NavigationTabProps) => {
  const { pathname } = useLocation();

  return (
    <S.NavContainer>
      <S.NavList>
        {tabList.map((tab) => (
          <NavItem
            key={tab.label}
            label={tab.label}
            $isActiveTab={pathname.includes(tab.activePath)}
            onClick={tab.handleTabClick}
          />
        ))}
      </S.NavList>
    </S.NavContainer>
  );
};

export default NavigationTab;
