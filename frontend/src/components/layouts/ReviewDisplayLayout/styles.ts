import styled from '@emotion/styled';

export const ReviewDisplayLayout = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  min-height: inherit;
`;

export const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  @media screen and (max-width: 500px) {
    flex-direction: column;
    align-items: flex-start;
    margin-bottom: 2.5rem;
  }
`;
