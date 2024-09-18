import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

interface MainContainerProps {
  $isShowBreadCrumb?: boolean;
}
const calculateMinHeight = ({ $isShowBreadCrumb, ...theme }: MainContainerProps & Theme) => {
  const topbarHeight = theme.componentHeight.topbar;
  const footerHeight = theme.componentHeight.footer;
  const breadCrumbHeight = $isShowBreadCrumb ? theme.componentHeight.breadCrumb : '0rem';

  return `calc(100vh - ${topbarHeight} - ${footerHeight} - ${breadCrumbHeight})`;
};

export const MainContainer = styled.div<MainContainerProps>`
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
  max-width: ${({ theme }) => theme.breakpoints.medium + 'px'};
  height: 100%;
  min-height: inherit;

  border-radius: 0.5rem;
`;
