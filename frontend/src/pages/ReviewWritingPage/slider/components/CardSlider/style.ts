import styled from '@emotion/styled';

export const Slide = styled.div`
  flex: 0 0 100%;

  box-sizing: border-box;
  height: fit-content;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-end;

  padding-right: 2.5rem;
  padding-bottom: 2rem;
  button {
    width: auto;
    min-width: 8rem;
    height: 3.5rem;
  }

  @media screen and (max-width: 375px) {
    gap: 1rem;
  }
`;
