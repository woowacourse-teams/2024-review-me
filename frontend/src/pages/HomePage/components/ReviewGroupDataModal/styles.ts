import styled from '@emotion/styled';

export const ReviewGroupDataModal = styled.div`
  display: flex;
  flex-direction: column;

  width: 52rem;
  height: 18rem;
`;

export const ReviewGroupDataTitle = styled.h3`
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  font-size: 2rem;
  margin-bottom: 4.5rem;
`;

export const ReviewGroupDataContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;

export const ReviewGroupDataItem = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 1.8rem;
  align-items: center;
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

export const WarningContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;

  margin-top: 3.7rem;
`;

export const CheckContainer = styled.div`
  font-size: 1.5rem;

  display: flex;
  align-items: center;
  gap: 0.6rem;
`;

export const Checkbox = styled.input`
  height: 1rem;
  width: 1rem;
  margin-right: 0.3rem;

  cursor: pointer;
`;

export const CheckMessage = styled.p`
  font-size: 1.3rem;
`;

export const Warning = styled.p`
  color: ${({ theme }) => theme.colors.red};
  font-size: smaller;
`;
