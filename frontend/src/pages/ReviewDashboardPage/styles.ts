import styled from '@emotion/styled';

export const ReviewDashboardPage = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const RevieweeGuide = styled.p`
  font-size: larger;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
