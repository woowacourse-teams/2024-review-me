import styled from '@emotion/styled';

export const RevieweeComments = styled.p`
  width: inherit;
  height: 3rem;
  margin-top: 1.6rem;
  padding-left: 2.5rem;

  font-size: ${({ theme }) => theme.fontSize.basic};
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  border-left: 0.4rem solid ${({ theme }) => theme.colors.black};
`;
