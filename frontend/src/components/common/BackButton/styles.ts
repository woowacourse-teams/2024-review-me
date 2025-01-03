import styled from '@emotion/styled';

interface BackButtonStyleProps {
  $style?: React.CSSProperties;
}

export const BackButton = styled.button<BackButtonStyleProps>`
  width: 3.5rem;
  height: 3.5rem;

  ${({ $style }) => $style && { ...$style }}
`;

export const BackButtonImage = styled.img``;
