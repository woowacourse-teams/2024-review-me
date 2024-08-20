import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

interface MainContainerProps {
  $isBreadCrumb: boolean;
}
const calculateMinHeight = ({ $isBreadCrumb, ...theme }: MainContainerProps & Theme) => {
  const topbarHeight = theme.componentHeight.topbar;
  const footerHeight = theme.componentHeight.footer;
  const breadCrumbHeight = $isBreadCrumb ? theme.componentHeight.breadCrumb : '0rem';

  return `calc(100vh - ${topbarHeight} - ${footerHeight} - ${breadCrumbHeight})`;
};

export const MainContainer = styled.div<MainContainerProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  min-height: ${({ theme, $isBreadCrumb }) => css(calculateMinHeight({ $isBreadCrumb, ...theme }))};
  margin-bottom: ${({ theme }) => theme.componentHeight.footer};
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: 100%;
  max-width: ${({ theme }) => theme.breakpoints.desktop};
  height: 100%;
  min-height: inherit;

  border-radius: 0.5rem;
`;
