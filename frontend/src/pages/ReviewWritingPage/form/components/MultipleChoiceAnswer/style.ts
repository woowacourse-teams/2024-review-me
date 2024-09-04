import styled from '@emotion/styled';

export const LimitGuideMessage = styled.div`
  width: fit-content;
  height: 2.4rem;
  margin-top: 2.5rem;
  p {
    font-weight: ${({ theme }) => theme.fontWeight.semibold};
    color: ${({ theme }) => theme.colors.primary};
    border-radius: ${({ theme }) => theme.borderRadius.basic};
  }
`;

export const ConfirmModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: fit-content;

  white-space: nowrap;
`;

export const ConfirmModalTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
