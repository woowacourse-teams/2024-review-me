import styled from '@emotion/styled';

export const ReviewItem = styled.article`
  display: flex;
  flex-direction: column;
  margin-bottom: 1.6rem;

  & > * {
    font-weight: 600;
  }
`;

export const ReviewQuestion = styled.div`
  &::after {
    content: ' (20자 이상)';
  }
`;

export const ReviewTextarea = styled.textarea`
  resize: none;

  width: 100%;
  max-width: 100%;
  height: 10rem;
  margin-top: 0.8rem;
  padding: 1.6rem;

  border-radius: 1.6rem;
`;
