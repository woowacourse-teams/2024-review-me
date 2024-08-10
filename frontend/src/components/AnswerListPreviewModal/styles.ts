import styled from '@emotion/styled';

export const AnswerListContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10rem;
`;

export const CardLayout = styled.div`
  position: relative;

  overflow: hidden;

  width: ${({ theme }) => theme.formWidth};

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
`;
