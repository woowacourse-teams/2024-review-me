import styled from '@emotion/styled';

export const Loader = styled.div`
  animation: l1 1s linear infinite alternate;

  @keyframes l1 {
    to {
      opacity: 0;
    }
  }
`;
