import styled from '@emotion/styled';

export const Line = styled.p`
  min-height: calc(${({ theme }) => theme.fontSize.basic} * 1.5);
  word-break: break-all;
  overflow-wrap: break-word;
  white-space: normal;
`;
