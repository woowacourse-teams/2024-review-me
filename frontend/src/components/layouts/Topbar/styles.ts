import styled from '@emotion/styled';

export const Layout = styled.section`
  display: flex;
  justify-content: space-between;

  box-sizing: border-box;
  width: 100%;
  height: ${({ theme }) => theme.componentHeight.topbar};
  padding: 2rem 2.5rem;

  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
`;

export const Container = styled.div`
  display: flex;
  gap: 2rem;
  align-items: center;
  height: 100%;
`;

export const UserProfile = styled.img`
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
`;
