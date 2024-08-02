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

export const ReviewTextarea = styled.textarea<{ $isError: boolean }>`
  resize: none;

  overflow-y: auto;

  width: 100%;
  max-width: 100%;
  height: 15rem;
  padding: 1.6rem;

  font-weight: ${({ theme }) => theme.fontWeight.medium};

  border: 0.1rem solid ${({ $isError, theme }) => ($isError ? theme.colors.red : theme.colors.black)};
  border-radius: 0.8rem;

  &::placeholder {
    font-weight: ${({ theme }) => theme.fontWeight.medium};
  }
`;

export const Container = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const ReviewTextLength = styled.p`
  display: flex;
  justify-content: flex-end;
  margin: 0;
`;

export const ReviewTextareaError = styled.p`
  color: ${({ theme }) => theme.colors.red};
`;
