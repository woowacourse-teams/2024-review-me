import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewInfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  margin-top: 2rem;
  margin-left: 1rem;
`;

export const ProjectName = styled.span`
  font-size: ${({ theme }) => theme.fontSize.large};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  ${media.medium} {
    font-size: 3rem;
  }

  ${media.small} {
    font-size: 2.8rem;
  }

  ${media.xSmall} {
    font-size: 2.6rem;
  }
`;

export const RevieweeInfoWrapper = styled.div`
  font-size: ${({ theme }) => theme.fontSize.mediumSmall};

  ${media.medium} {
    font-size: ${({ theme }) => theme.fontSize.basic};
  }

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
