import { useLocation } from 'react-router';

import Breadcrumb from '@/components/common/Breadcrumb';
import NavigationTab from '@/components/common/NavigationTab';
import { ROUTE } from '@/constants';
import useBreadcrumbPaths from '@/hooks/useBreadcrumbPaths';
import useNavigationTabs from '@/hooks/useNavigationTabs';
import { EssentialPropsWithChildren } from '@/types';

import Footer from '../Footer';
import Main from '../Main';
import Topbar from '../Topbar';

import * as S from './styles';

interface PageLayoutProps {
  isNeedBreadCrumb?: boolean;
}

const PageLayout = ({ children, isNeedBreadCrumb = true }: EssentialPropsWithChildren<PageLayoutProps>) => {
  // TODO: NavigationTab은 사용자가 홈 페이지와 연결 페이지가 아닌 다른 페이지에서 로그인 상태일 때만 보여준다.
  // 임시로 true 설정 (로그인 기능 추가하면서 여기도 수정해야 한다.)
  const isUserLoggedIn = true;

  const { pathname } = useLocation();

  const breadcrumbPathList = useBreadcrumbPaths();
  const navigationTabList = useNavigationTabs();
  const isShowBreadCrumb = !isUserLoggedIn && isNeedBreadCrumb && breadcrumbPathList.length > 1;
  const isShowNavigationTab = isUserLoggedIn && pathname !== '/' && !pathname.includes(ROUTE.reviewZone);

  return (
    <S.Layout>
      <S.Wrapper>
        <Topbar />
        {isShowNavigationTab && <NavigationTab tabList={navigationTabList} />}
        {isShowBreadCrumb && <Breadcrumb pathList={breadcrumbPathList} />}
        <Main isShowBreadCrumb={isShowBreadCrumb} isShowNavigationTab={isShowNavigationTab}>
          {children}
        </Main>
        <Footer />
      </S.Wrapper>
    </S.Layout>
  );
};

export default PageLayout;
