import styled from '@emotion/styled';

export const ReviewAccessFormContent = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

export const ReviewAccessFormBody = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const ErrorMessage = styled.p`
  padding-left: 0.7rem;
  font-size: 1.3rem;
  color: ${({ theme }) => theme.colors.red};
`;
