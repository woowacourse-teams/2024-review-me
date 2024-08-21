import styled from '@emotion/styled';

export const Layout = styled.section`
  height: 70vh;
`;

export const Container = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 5rem;
  align-items: center;
  justify-content: center;
`;

export const Title = styled.p`
  font-size: 3rem;
  font-weight: bold;
`;

export const HomeIcon = styled.img`
  width: 2rem;
  height: 2rem;
`;

export const HomeText = styled.p`
  margin-left: 0.5rem;
`;
