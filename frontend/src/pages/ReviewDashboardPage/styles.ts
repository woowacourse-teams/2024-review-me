import styled from '@emotion/styled';

export const ReviewDashboardPage = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const DashboardMainImg = styled.img`
  width: 47rem;
  height: 32rem;
`;

export const RevieweeGuide = styled.p`
  font-size: 2.2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;

  margin-top: 2rem;
`;
