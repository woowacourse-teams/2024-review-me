import styled from '@emotion/styled';

import media from '@/utils/media';

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
  margin-top: 1.5rem;

  ${media.xSmall} {
    gap: 2rem;
  }
`;

export const ButtonTextContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  ${media.xSmall} {
    gap: 0.5rem;
  }
`;

export const ButtonText = styled.p`
  font-size: 2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  ${media.small} {
    font-size: 1.8rem;
  }
`;

export const ButtonDescription = styled.p`
  font-size: 1.3rem;

  ${media.small} {
    font-size: 1.1rem;
  }
`;
