import styled from '@emotion/styled';

import media from '@/utils/media';

export const Description = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  margin: 0;
  padding: 1rem 3rem;

  ${media.xSmall} {
    padding: 1rem 1.2rem;
  }

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic} 0 0;
`;

export const DescriptionSide = styled.div`
  display: flex;
  width: 100%;
  flex-wrap: wrap;
`;

export const ProjectInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  width: 100%;
  margin: 0 1rem;
`;

export const ProjectName = styled.p`
  margin-top: 0;
  font-size: ${({ theme }) => theme.fontSize.medium};

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }

  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const RevieweeNameAndDateContainer = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;

  font-size: ${({ theme }) => theme.fontSize.basic};

  ${media.small} {
    flex-direction: column;
  }

  ${media.xSmall} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;

export const RevieweeNameWrapper = styled.p`
  margin-top: 0;
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
