import styled from '@emotion/styled';

export const TopButton = styled.button`
  position: fixed;
  right: 5rem;
  bottom: 5rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 5rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.primary};
  filter: drop-shadow(0 0 0.2rem ${({ theme }) => theme.colors.primary});
  border: 0.2rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 100%;
`;

export const ArrowImage = styled.img`
  width: 3rem;
  height: 3rem;
`;
