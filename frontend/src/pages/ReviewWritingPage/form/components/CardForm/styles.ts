import styled from '@emotion/styled';

import media, { breakpoints } from '@/utils/media';

export const CardForm = styled.form`
  position: relative;
  overflow: hidden;
  width: ${({ theme }) => theme.formWidth};
  overflow-wrap: break-word;

  ${media.small`
    width: 100%;
    `}
`;

export const RevieweeDescription = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 2rem;

  ${breakpoints.small} {
    padding-left: ${({ theme }) => theme.breadcrumbSize.paddingLeft};
  }
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

  ${breakpoints.small} {
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
