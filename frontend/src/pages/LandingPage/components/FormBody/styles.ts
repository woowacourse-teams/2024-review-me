import styled from '@emotion/styled';

export const FormBody = styled.div<{ direction: React.CSSProperties['flexDirection'] }>`
  display: flex;
  flex-direction: ${({ direction }) => direction};
  gap: 1.6em;
`;
