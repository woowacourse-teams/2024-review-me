import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

interface MainContainerProps {
  $isShowBreadCrumb?: boolean;
  $isShowNavigationTab?: boolean;
}
const calculateMinHeight = ({ $isShowBreadCrumb, $isShowNavigationTab, ...theme }: MainContainerProps & Theme) => {
  const topbarHeight = theme.componentHeight.topbar;
  const footerHeight = theme.componentHeight.footer;
  const breadCrumbHeight = $isShowBreadCrumb ? theme.componentHeight.breadCrumb : '0rem';
  const navigationTabHeight = $isShowNavigationTab ? theme.componentHeight.navigationTab : '0rem';

  return `calc(100vh - ${topbarHeight} - ${footerHeight} - ${breadCrumbHeight}) - ${navigationTabHeight}`;
};

export const MainContainer = styled.div<MainContainerProps>`
  z-index: ${({ theme }) => theme.zIndex.main};

  display: flex;
  align-items: center;
  justify-content: center;

  min-height: ${({ theme, $isShowBreadCrumb }) => css(calculateMinHeight({ $isShowBreadCrumb, ...theme }))};
  margin-bottom: ${({ theme }) => theme.componentHeight.footer};
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: 100%;
  max-width: ${({ theme }) => theme.breakpoint.medium}px;
  height: 100%;
  min-height: inherit;

  border-radius: 0.5rem;
`;
