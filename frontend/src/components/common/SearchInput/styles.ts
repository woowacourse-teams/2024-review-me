import styled from '@emotion/styled';

interface InputProps {
  $width: string;
  $height: string;
}
export const Input = styled.input<InputProps>`
  width: ${(props) => props.$width};
  height: ${(props) => props.$height};
  padding: 1.6rem;

  border: 1px solid ${({ theme }) => theme.colors.black};
  border-radius: 1.5rem;
`;
