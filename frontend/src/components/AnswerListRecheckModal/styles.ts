import styled from '@emotion/styled';

export const AnswerListContainer = styled.div``;

export const CardLayout = styled.div`
  display: flex;
  flex-direction: column;

  gap: 1.2rem;
  position: relative;

  overflow: hidden;

  width: ${({ theme }) => theme.formWidth};
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
`;

export const ReviewWritingCardWrapper = styled.div`
  overflow: hidden;
  border: 0.2rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;
