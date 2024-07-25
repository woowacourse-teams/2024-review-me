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
`;
export const ProjectNameAndDateContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-left: 1rem;
`;

export const ProjectName = styled.p`
  margin-top: 0;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
