import styled from '@emotion/styled';

export const CheckboxItem = styled.div`
  display: flex;
  margin-bottom: 1rem;
`;

export const CheckboxLabel = styled.label`
  display: flex;
  gap: 0.7rem;
  font-size: ${({ theme }) => theme.fontSize.basic};
  line-height: 1.5;
`;
