import styled from '@emotion/styled';

export const ReviewItem = styled.article`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;

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

  overflow-y: auto;

  width: 100%;
  max-width: 100%;
  height: 15rem;
  padding: 1.6rem;

  border-radius: 0.8rem;
`;
