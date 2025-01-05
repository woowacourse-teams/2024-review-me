import styled from '@emotion/styled';

interface BackButtonStyleProps {
  $style?: React.CSSProperties;
}

export const BackButtonWrapper = styled.div<BackButtonStyleProps>`
  display: flex;
  justify-content: flex-start;
  width: 100%;

  ${({ $style }) => $style && { ...$style }}
`;

export const BackButton = styled.button<BackButtonStyleProps>`
  width: 3.5rem;
  height: 3.5rem;

  ${({ $style }) => $style && { ...$style }}
`;
