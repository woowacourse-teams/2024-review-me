import styled from '@emotion/styled';

export const ReviewDisplayLayoutContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  min-height: inherit;

  gap: 2rem;

  margin: 2rem;
`;

export const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  @media screen and (max-width: 530px) {
    flex-direction: column;
    align-items: flex-start;

    margin-bottom: 2rem;
  }
`;
