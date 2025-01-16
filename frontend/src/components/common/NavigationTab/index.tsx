import useNavigationTabs from '@/hooks/useNavigationTabs';

import NavItem from './NavItem';
import * as S from './styles';

const NavigationTab = () => {
  const { currentTabIndex, tabList } = useNavigationTabs();

  return (
    <S.NavContainer>
      <S.NavList>
        {tabList.map((tab, index) => {
          return (
            <NavItem
              key={tab.label}
              label={tab.label}
              $isSelected={currentTabIndex === index}
              onClick={tab.handleTabClick}
            />
          );
        })}
      </S.NavList>
    </S.NavContainer>
  );
};

export default NavigationTab;
