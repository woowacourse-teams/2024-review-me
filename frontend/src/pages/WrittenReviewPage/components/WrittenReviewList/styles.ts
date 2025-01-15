import styled from '@emotion/styled';

export const WrittenReviewList = styled.ul`
  overflow-x: hidden;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 1.7rem;

  max-height: 68vh;

  & > li {
    margin-right: 0.5rem;
  }
`;
