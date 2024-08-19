import styled from '@emotion/styled';

import { OverviewItemStyleProps } from './';

export const OverviewItem = styled.div<OverviewItemStyleProps>`
  display: flex;
  flex-direction: ${({ direction }) => direction};
  align-items: center;
  justify-content: center;
  gap: 2rem;

  background-color: ${({ theme }) => theme.colors.white};

  /* NOTE: 투명도가 반영된 색이라 그런지 피그마에서의 색과 다름 */
  /* background-color: ${({ theme }) => theme.colors.sidebarBackground}; */

  padding: 0 2rem;

  ${({ direction }) =>
    direction === 'row'
      ? `
       width: 68rem;
       height: 23rem;
      `
      : `
          width: 30rem;
          height: 45rem;
      `}
`;

export const OverviewItemTitle = styled.p`
  font-size: 1.7rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const OverviewItemImg = styled.img<OverviewItemStyleProps>`
  object-fit: contain;

  ${({ direction }) =>
    direction === 'row'
      ? `
        width: 30rem;
        height: 22rem;
      `
      : `
          width: 15rem;
          height: 20rem;
      `}
`;

export const OverviewItemContent = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.3rem;
  padding: 0 2rem;
`;

export const OverviewItemDescription = styled.div`
  margin-bottom: 1.8rem;
`;
