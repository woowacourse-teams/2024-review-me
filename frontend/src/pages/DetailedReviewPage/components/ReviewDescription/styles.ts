import styled from '@emotion/styled';

export const Description = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  margin: 0;
  padding: 1rem 3rem;

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic} 0 0;
`;

export const DescriptionSide = styled.div`
  display: flex;
  width: 100%;
  flex-wrap: wrap;
`;

//NOTE: 6rem :깃헙 로고 사이즈
export const ProjectInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  /* width: calc(100% - 6rem); */
  width: 100%;
  margin: 0 1rem;
`;

export const ProjectName = styled.p`
  margin-top: 0;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  @media (max-width: 425px) {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }
`;

export const RevieweeNameAndDateContainer = styled.div`
  display: flex;
  justify-content: space-between;

  width: 100%;

  font-size: ${({ theme }) => theme.fontSize.basic};

  @media (max-width: 425px) {
    font-size: ${({ theme }) => theme.fontSize.small};
  }

  @media (max-width: 768px) {
    flex-direction: column;
  }
`;

export const RevieweeNameWrapper = styled.p`
  margin-top: 0;
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
