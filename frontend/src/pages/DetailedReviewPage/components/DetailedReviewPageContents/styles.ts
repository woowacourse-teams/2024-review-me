import styled from '@emotion/styled';

export const DetailedReviewPageContents = styled.div`
  width: ${({ theme }) => theme.formWidth};
  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const ReviewContentContainer = styled.div`
  margin-bottom: 7rem;
  padding: 0 4rem;
`;
