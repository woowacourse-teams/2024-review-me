import styled from '@emotion/styled';

export const LongReviewContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Question = styled.p`
  margin-bottom: 0.8rem;
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;

export const TextareaContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

interface TextareaProps {
  $isError: boolean;
  $style?: React.CSSProperties;
}

export const Textarea = styled.textarea<TextareaProps>`
  resize: none;

  overflow-y: auto;

  width: 100%;
  height: 15rem;
  padding: 1.6rem;

  font-weight: ${({ theme }) => theme.fontWeight.medium};

  border: 0.1rem solid ${({ $isError, theme }) => ($isError ? theme.colors.red : theme.colors.black)};
  border-radius: 0.8rem;

  &::placeholder {
    font-weight: ${({ theme }) => theme.fontWeight.medium};
  }

  ${({ $style }) => $style && { ...$style }};
`;

export const TextareaInfoContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 0.8rem;
`;

export const ReviewTextareaError = styled.p`
  color: ${({ theme }) => theme.colors.red};
`;

export const ReviewTextLength = styled.p`
  display: flex;
  justify-content: flex-end;
  margin: 0;
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;
