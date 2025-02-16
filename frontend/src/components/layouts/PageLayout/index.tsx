import { useLocation } from 'react-router';

import Breadcrumb from '@/components/common/Breadcrumb';
import NavigationTab from '@/components/common/NavigationTab';
import { ROUTE } from '@/constants';
import { useGetUserProfile } from '@/hooks/oAuth';
import useBreadcrumbPaths from '@/hooks/useBreadcrumbPaths';
import { EssentialPropsWithChildren } from '@/types';

import Footer from '../Footer';
import Main from '../Main';
import Topbar from '../Topbar';

import * as S from './styles';

interface PageLayoutProps {
  isNeedBreadCrumb?: boolean;
}

const PageLayout = ({ children, isNeedBreadCrumb = true }: EssentialPropsWithChildren<PageLayoutProps>) => {
  const { pathname } = useLocation();
  const { userProfile, isUserLoggedIn } = useGetUserProfile();

  const breadcrumbPathList = useBreadcrumbPaths();
  const isShowBreadCrumb = isNeedBreadCrumb && breadcrumbPathList.length > 1;
  const isShowNavigationTab = isUserLoggedIn && pathname !== ROUTE.home && !pathname.includes(ROUTE.reviewZone);

  return (
    <S.Layout>
      <S.Wrapper>
        <Topbar
          isUserLoggedIn={isUserLoggedIn}
          $hideBorderBottom={isShowNavigationTab}
          userProfile={userProfile ? userProfile : null}
        />
        {isShowBreadCrumb && <Breadcrumb pathList={breadcrumbPathList} />}
        {isShowNavigationTab && <NavigationTab />}
        <Main isShowBreadCrumb={isShowBreadCrumb}>{children}</Main>
        <Footer />
      </S.Wrapper>
    </S.Layout>
  );
};

export default PageLayout;
