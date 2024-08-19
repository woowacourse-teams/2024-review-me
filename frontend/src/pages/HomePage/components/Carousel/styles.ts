import styled from '@emotion/styled';

export const Layout = styled.section`
  width: 100%;
  padding-left: 3rem;
`;

export const ScrollContainer = styled.div<{ gap: number; offset: number }>`
  overflow-x: auto;
  display: flex;
  gap: ${({ gap }) => `${gap}rem`};

  height: 100%;
  padding: ${({ offset }) => `0 ${offset}rem`};

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const Page = styled.div<{ width: number; height: number }>`
  flex-shrink: 0;
  width: ${({ width }) => `${width}rem`};
  height: ${({ height }) => `${height}rem`};
`;

export const PageItem = styled.div`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  align-items: center;

  height: 100%;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 0.8rem;
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  box-shadow: 0.8rem 1rem 1.6rem hsl(0deg 0% 0% / 0.25);

  img {
    width: 100%;
    height: 25rem;
    background-color: ${({ theme }) => theme.colors.palePurple};
    border-radius: ${({ theme }) => theme.borderRadius.basic} ${({ theme }) => theme.borderRadius.basic} 0 0;
  }
`;

export const PageTitle = styled.p`
  margin: 1rem 0;
  font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const PageDescription = styled.div`
  width: 100%;
  padding-left: 3rem;
`;

export const DescriptionItem = styled.p`
  margin: 0.5rem 0;
  font-size: ${({ theme }) => theme.fontSize.basic};
`;

export const IndicatorWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 1.6rem;
`;

export const Indicator = styled.div<{ focused: boolean }>`
  cursor: pointer;

  width: 1rem;
  height: 1rem;
  margin: 0 0.5rem;

  background-color: ${({ focused, theme }) => (focused ? theme.colors.black : theme.colors.placeholder)};
  border-radius: 50%;

  transition: background-color 0.3s ease;
`;
