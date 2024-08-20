import styled from '@emotion/styled';

export const AnswerListContainer = styled.div``;

export const CardLayout = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  width: ${({ theme }) => theme.formWidth};
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ReviewCardWrapper = styled.div`
  overflow: hidden;
  border: 0.2rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const TextAnswerWrapper = styled.div`
  margin-top: 1rem;
`;

