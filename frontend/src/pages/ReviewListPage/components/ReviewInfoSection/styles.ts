import styled from '@emotion/styled';

export const ReviewInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ProjectName = styled.span`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;

export const RevieweeInfoWrapper = styled.div`
  font-size: ${({ theme }) => theme.fontSize.basic};
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
