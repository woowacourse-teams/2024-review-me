import styled from '@emotion/styled';

export const ReviewURLModal = styled.div`
  display: flex;
  flex-direction: column;
  width: 52rem;
  height: 18rem;
`;

export const ModalTitle = styled.p`
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  font-size: 2rem;
  margin-bottom: 4.5rem;
`;

export const ReviewURLModalItem = styled.div`
  display: flex;
  gap: 1.8rem;
  align-items: center;
  justify-content: space-between;

  font-size: 1.5rem;
`;

export const DataName = styled.span`
  flex: 0.6;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const Data = styled.span`
  flex: 2;
  color: ${({ theme }) => theme.colors.gray};
`;

export const WarningWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  margin-top: 3.7rem;
`;

export const CheckContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  font-size: 1.5rem;
`;

export const Checkbox = styled.input`
  cursor: pointer;
  width: 1rem;
  height: 1rem;
  margin-right: 0.3rem;
`;

export const CheckMessage = styled.p`
  font-size: 1.3rem;
`;

export const Warning = styled.p`
  font-size: smaller;
  color: ${({ theme }) => theme.colors.red};
`;
