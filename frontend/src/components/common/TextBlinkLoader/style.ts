import styled from '@emotion/styled';

export interface LoaderProps {
  $content?: string;
}

export const Loader = styled.div<LoaderProps>`
  animation: l1 1s linear infinite alternate;

  &::before {
    content: '${({ $content }) => $content ?? 'Loading...'}';
  }

  @keyframes l1 {
    to {
      opacity: 0;
    }
  }
`;
