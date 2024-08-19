import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const NullText = styled.span`
  font-size: 25rem;
  font-weight: ${({ theme }) => theme.fontWeight.bolder};
  color: #e0e1e3;
`;

export const EmptyReviewsText = styled.span`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
