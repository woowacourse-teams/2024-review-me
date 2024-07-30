import styled from '@emotion/styled';

export const LoadingBar = styled.div`
  position: relative;

  display: block;

  width: 30rem;
  height: 2rem;

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: 3rem;

  ::before {
    content: '';

    position: absolute;
    top: 0;
    left: 0;

    width: 0%;
    height: 100%;

    background: ${({ theme }) => theme.colors.primary};
    border-radius: 3rem;

    animation: moving 1s ease-in-out infinite;
  }

  @keyframes moving {
    50% {
      width: 100%;
    }

    100% {
      right: 0;
      left: unset;
      width: 0;
    }
  }
`;
