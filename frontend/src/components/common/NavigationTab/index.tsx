import useNavigationTabs from '@/hooks/useNavigationTabs';

import NavItem from './NavItem';
import * as S from './styles';

interface NavigationTabProps {
  selectedTab: '리뷰 링크 관리' | '작성한 리뷰 확인';
}

const NavigationTab = ({ selectedTab }: NavigationTabProps) => {
  const { currentTabIndex, tabList } = useNavigationTabs({ selectedTab });

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
