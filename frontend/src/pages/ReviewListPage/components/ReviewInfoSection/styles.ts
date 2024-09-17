import styled from '@emotion/styled';

export const ReviewInfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  margin-top: 2rem;
  margin-left: 1rem;
`;

export const ProjectName = styled.span`
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  @media (max-width: 425px) {
    font-size: ${({ theme }) => theme.fontSize.medium};
  }

  @media (min-width: 425px) and (max-width: 767px) {
    font-size: 2.8rem;
  }

  @media (min-width: 768px) and (max-width: 1024px) {
    font-size: 3rem;
  }

  @media (min-width: 1025px) {
    font-size: ${({ theme }) => theme.fontSize.large};
  }
`;

export const RevieweeInfoWrapper = styled.div`
  @media (min-width: 426px) {
    font-size: ${({ theme }) => theme.fontSize.small};
  }

  @media (min-width: 768px) and (max-width: 1024px) {
    font-size: ${({ theme }) => theme.fontSize.basic};
  }

  @media (min-width: 1025px) {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
