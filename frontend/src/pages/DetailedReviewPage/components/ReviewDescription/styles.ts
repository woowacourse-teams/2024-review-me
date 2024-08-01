import styled from '@emotion/styled';

export const Description = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 6rem;
  margin: 0;
  padding-left: 0;
`;
export const DescriptionSide = styled.div`
  display: flex;
  width: 100%;
`;
//NOTE: 6rem :깃헙 로고 사이즈
export const ProjectInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  width: calc(100% - 6rem);
  margin-left: 1rem;
`;

export const ProjectName = styled.p`
  margin-top: 0;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const RevieweeNameAndDateContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;

  font-size: ${({ theme }) => theme.fontSize.basic};
`;

export const RevieweeNameWrapper = styled.p`
  margin-top: 0;
`;
export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;
