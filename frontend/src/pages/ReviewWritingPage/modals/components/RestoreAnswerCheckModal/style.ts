import styled from '@emotion/styled';

import media from '@/utils/media';

export const RestoreAnswerCheckModal = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;

  width: max-content;
  p {
    width: fit-content;
    text-align: center;
  }

  ${media.xSmall} {
    width: 23rem;
  }
`;

export const ConfirmModalTitle = styled.p`
  margin-bottom: 1rem;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
