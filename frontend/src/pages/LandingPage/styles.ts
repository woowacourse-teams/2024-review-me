import styled from '@emotion/styled';

import { FormDirection } from './components/FormLayout';

export const LandingPage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 7.7rem;

  margin-top: 4rem;
`;

export const FormLayout = styled.form`
  display: flex;
  flex-direction: column;

  width: 40rem;
`;

export const Title = styled.h2`
  font-size: ${({ theme }) => theme.fontSize.basic};

  margin-bottom: 2.2rem;
`;

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

export const FormBody = styled.div<{ direction: React.CSSProperties['flexDirection'] }>`
  display: flex;
  flex-direction: ${({ direction }) => direction};
  gap: 1.6em;
`;

export const ErrorMessage = styled.p`
  font-size: 1.3rem;

  color: ${({ theme }) => theme.colors.red};

  padding-left: 0.7rem;
`;
