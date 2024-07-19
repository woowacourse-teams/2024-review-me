import styled from '@emotion/styled';

export const ReviewItem = styled.article`
  display: flex;
  flex-direction: column;

  margin-bottom: 1.6rem;

  & > * {
    font-weight: 600;
  }
`;

export const ReviewQuestion = styled.div``;

export const ReviewTextarea = styled.textarea`
  width: 100%;
  height: 10rem;
  max-width: 100%;

  padding: 1.6rem;
  border-radius: 1.6rem;
  margin-top: 0.8rem;

  resize: none;
`;
