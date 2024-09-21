import { TopButton } from '@/components/common';
import Breadcrumb from '@/components/common/Breadcrumb';
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
  const breadcrumbPathList = useBreadcrumbPaths();
  const isShowBreadCrumb = isNeedBreadCrumb && breadcrumbPathList.length > 1;

  return (
    <S.Layout>
      <S.Wrapper>
        <Topbar />
        {isShowBreadCrumb && <Breadcrumb pathList={breadcrumbPathList} />}
        <Main isShowBreadCrumb={isShowBreadCrumb}>{children}</Main>
        <Footer />
      </S.Wrapper>
      <TopButton />
    </S.Layout>
  );
};

export default PageLayout;
