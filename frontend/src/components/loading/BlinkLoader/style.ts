import styled from '@emotion/styled';

export interface LoaderProps {
  $animationDurationTime?: string;
}

export const Loader = styled.div<LoaderProps>`
  animation: l1 ${(props) => props.$animationDurationTime ?? '1s'} linear infinite alternate;

  @keyframes l1 {
    to {
      opacity: 0;
    }
  }
`;
