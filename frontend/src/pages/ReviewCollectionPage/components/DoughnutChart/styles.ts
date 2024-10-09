import styled from '@emotion/styled';

import media from '@/utils/media';

export const DoughnutChartContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  gap: 5rem;

  ${media.small} {
    flex-direction: column;
  }
`;
