import styled from '@emotion/styled';

interface InputProps {
  $width: string;
  $height: string;
}
export const Input = styled.input<InputProps>`
  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 1.5rem;
  height: ${(props) => props.$height};
  width: ${(props) => props.$width};
  padding: 1.6rem;
`;
