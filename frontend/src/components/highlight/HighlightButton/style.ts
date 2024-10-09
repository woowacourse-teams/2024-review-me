import styled from '@emotion/styled';

export const Button = styled.button`
  display: flex;
  gap: 0.8rem;

  width: fit-content;
  padding: 0.5rem 0.8rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  -webkit-box-shadow: 0 0 1.4rem -0.2rem #343434;
  box-shadow: 0 0 1.4rem -0.2rem #343434;

  &:hover {
    background-color: ${({ theme }) => theme.colors.palePurple};
  }
`;

export const ButtonIcon = styled.img`
  width: 1.5rem;
  height: 1.5rem;
`;

export const Color = styled.div`
  width: 1.5rem;
  height: 1.5rem;
  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;
