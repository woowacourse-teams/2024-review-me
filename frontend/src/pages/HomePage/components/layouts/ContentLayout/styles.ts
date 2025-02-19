import styled from '@emotion/styled';

import media from '@/utils/media';

export const LoginForm = styled.div`
  display: flex;
  flex-direction: column;

  gap: 2rem;
`;

export const Title = styled.h2`
  font-size: 2.5rem;
  white-space: nowrap;

  ${media.medium} {
    font-size: 2rem;
  }

  ${media.small} {
    font-size: 2.5rem;
  }

  ${media.xSmall} {
    font-size: 1.8rem;
  }

  ${media.xxSmall} {
    font-size: 1.6rem;
  }
`;

export const SubTitleWrapper = styled.div``;

export const SubTitle = styled.p`
  font-size: ${({ theme }) => theme.fontSize.basic};
  color: ${({ theme }) => theme.colors.disabledText};
`;
