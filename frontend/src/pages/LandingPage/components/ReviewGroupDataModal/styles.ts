import styled from '@emotion/styled';

export const ReviewGroupDataModal = styled.div`
  display: flex;
  flex-direction: column;

  width: 52rem;
  height: 23rem;

  gap: 4rem;
`;

export const ReviewGroupDataTitle = styled.h3`
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  font-size: 2rem;
`;

export const ReviewGroupDataContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.7rem;
  min-width: 0;
`;

export const ReviewGroupDataItem = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 1.8rem;

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
  height: 1rem;
  width: 1rem;
  margin-right: 0.5rem;

  cursor: pointer;
`;

export const CheckMessage = styled.p``;

export const Warning = styled.p`
  color: ${({ theme }) => theme.colors.red};
  font-size: smaller;
`;
