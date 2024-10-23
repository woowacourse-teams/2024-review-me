import styled from '@emotion/styled';

import media from '@/utils/media';

export const DoughnutChartContainer = styled.div`
  display: flex;
  gap: 5rem;
  align-items: center;
  justify-content: center;

  ${media.small} {
    flex-direction: column;
  }
`;
