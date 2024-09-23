import styled from '@emotion/styled';

import media from '@/utils/media';

export const CardFormContainer = styled.div`
  position: relative;

  width: fit-content;
  min-width: ${({ theme }) => theme.formWidth};
  max-width: 90rem;

  ${media.medium} {
    width: 80vw;
    min-width: initial;
    margin: 0 auto;
    margin-top: 2.4rem;
  }

  @media screen and (max-width: 500px) {
    width: 95vw;
  }
`;
export const CardForm = styled.form`
  position: relative;
  overflow: hidden;
  width: 100%;
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

  ${media.small} {
    font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  }
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
