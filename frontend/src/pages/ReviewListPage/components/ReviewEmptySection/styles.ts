import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const NullText = styled.span`
  transform: rotate(-20deg);

  font-size: 25rem;
  font-weight: ${({ theme }) => theme.fontWeight.bolder};
  color: #3f393b;
  text-shadow: 2rem 2rem 0.7rem rgba(0, 0, 0, 0.2);
`;

export const EmptyReviewsText = styled.span`
  margin-top: 3rem;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.disabledText};
`;
