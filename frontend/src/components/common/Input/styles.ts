import styled from '@emotion/styled';

export const Input = styled.input<{ $width?: string; $height?: string }>`
  color: ${({ theme }) => theme.colors.black};

  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  width: ${({ $width }) => $width || 'auto'};
  height: ${({ $height }) => $height || 'auto'};

  padding: 1.2rem 1.6rem;
  font-size: 1.3rem;

  ::placeholder {
    color: ${({ theme }) => theme.colors.placeholder};
  }

  &:focus {
    border-color: ${({ theme }) => theme.colors.primaryHover};
  }
`;
