import styled from '@emotion/styled';

interface InputProps {
  $width: string;
  $height: string;
}
export const Input = styled.input<InputProps>`
  width: ${(props) => props.$width};
  height: ${(props) => props.$height};
  padding: 1.6rem;

  border: 0.1rem solid ${({ theme }) => theme.colors.black};
  border-radius: 0.8rem;

  &::placeholder {
    font-size: 1.2rem;
    color: ${({ theme }) => theme.colors.placeholder};
  }
`;
