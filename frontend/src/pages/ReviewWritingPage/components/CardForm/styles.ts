import styled from '@emotion/styled';

export const CardForm = styled.form`
  position: relative;
  overflow: hidden;
  width: ${({ theme }) => theme.formWidth};
  overflow-wrap: break-word;
`;

export const RevieweeDescription = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 2rem;
`;
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

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;

export const ConfirmModalMessage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: start;

  p {
    width: max-content;
    margin: 0;
  }
`;
