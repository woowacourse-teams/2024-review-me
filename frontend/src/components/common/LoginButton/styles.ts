import styled from '@emotion/styled';

interface LoginButtonStyleProps {
  $logoStyle?: React.CSSProperties;
}

export const ButtonLabelContainer = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
`;

export const LogoImg = styled.img<LoginButtonStyleProps>`
  width: 3.2rem;
  height: 3.2rem;

  ${({ $logoStyle }) => $logoStyle && { ...$logoStyle }};
`;
