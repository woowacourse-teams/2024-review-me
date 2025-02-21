import styled from '@emotion/styled';

export const ReviewZoneURLModal = styled.div`
  display: flex;
  flex-direction: column;
  width: 52rem;
`;

export const RequestURLContainer = styled.div`
  display: flex;
  align-items: center;
  max-width: 100%;
`;

export const ModalTitle = styled.p`
  margin-bottom: 4.2rem;
  font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ReviewZoneURLModalItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: flex-start;

  font-size: 1.5rem;
`;

export const DataName = styled.span`
  margin-right: 1rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  white-space: nowrap;
`;

export const Data = styled.span`
  flex: 2;
  color: ${({ theme }) => theme.colors.gray};
  word-break: break-all;
`;

export const CheckContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  margin-top: 2.5rem;
`;

export const Checkbox = styled.input`
  cursor: pointer;
  width: 0.8rem;
  height: 0.8rem;
  margin-right: 0.3rem;
`;

export const CheckMessage = styled.p`
  font-size: 1.5rem;
`;

export const WarningMessage = styled.p`
  margin-top: 0.5rem;
  font-size: 1.3rem;
  color: ${({ theme }) => theme.colors.red};
`;
