import styled from '@emotion/styled';

interface ContainerProps {
  $width: string;
  $height: string;
}
export const Container = styled.div<ContainerProps>`
  position: relative;
  width: ${(props) => props.$width};
  height: ${(props) => props.$height};
`;
export const ImgWrapper = styled.div<{ $isLoaded: boolean }>`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  opacity: ${(props) => (props.$isLoaded ? 1 : 0)};

  transition: opacity 300ms;
`;
export const ImgSkeleton = styled.div`
  width: 100%;
  height: 100%;

  background-image: linear-gradient(
    135deg,
    ${({ theme }) => theme.colors.lightGray} 40%,
    rgba(246, 246, 246, 0.89) 50%,
    ${({ theme }) => theme.colors.lightGray} 85%
  );
  background-size: 200% 100%;
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  animation: skeleton-animation 1.5s infinite linear;

  @keyframes skeleton-animation {
    0% {
      background-position: 200% 0;
    }
    100% {
      background-position: -200% 0;
    }
  }
`;
