import styled from '@emotion/styled';

export const ProfileTabContainer = styled.section`
  position: absolute;
  top: 5rem;
  right: 0;

  display: flex;
  flex-direction: column;

  width: max-content;
  max-width: 25rem;
  height: fit-content;
  padding: 1rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 0.8rem;
  box-shadow:
    0 0.5rem 0.5rem -0.3rem rgba(0, 0, 0, 0.2),
    0 0.8rem 1rem 0.1rem rgba(0, 0, 0, 0.14),
    0 0.3rem 1.4rem 0.2rem rgba(0, 0, 0, 0.12);
`;
