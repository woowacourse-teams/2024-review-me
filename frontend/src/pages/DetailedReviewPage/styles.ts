import styled from '@emotion/styled';

export const DetailReviewOuter = styled.div`
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;

  @media screen and (min-width: 1025px) {
    width: 1025px;
    margin: 0 auto;
  }
`;

export const Header = styled.div`
  height: 3rem;
  width: 100vw;
  border: 1px solid black;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
  background-color: #ffff;
`;
export const DetailedReview = styled.div`
  padding: 1rem;
  width: 40rem;
  margin-top: 3rem;
  border: 1px solid black;
  min-height: calc(100vh - 3rem);
  box-sizing: border-box;
`;
