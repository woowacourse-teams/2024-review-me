import styled from '@emotion/styled';

export const ReviewGroupDataModal = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;

  width: 52rem;
  height: 23rem;
`;

export const ReviewGroupDataTitle = styled.h3`
  font-size: 2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ReviewGroupDataContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.7rem;
  min-width: 0;
`;

export const ReviewGroupDataItem = styled.div`
  display: flex;
  gap: 2.1rem;
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

export const CheckContainer = styled.div`
  display: flex;
  font-size: 1.5rem;
`;

export const Checkbox = styled.input`
  cursor: pointer;
  width: 1rem;
  height: 1rem;
  margin-right: 0.5rem;
`;

export const CheckMessage = styled.p``;

export const Warning = styled.p`
  font-size: smaller;
  color: ${({ theme }) => theme.colors.red};
`;
