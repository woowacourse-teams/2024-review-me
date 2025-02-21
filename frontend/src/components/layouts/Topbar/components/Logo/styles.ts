import styled from '@emotion/styled';

import media from '@/utils/media';

export const Logo = styled.div`
  text-align: center;

  span {
    font-size: 2.8rem;
    font-weight: ${({ theme }) => theme.fontWeight.bolder};

    ${media.xSmall} {
      font-size: 2.6rem;
    }
  }

  span:last-child {
    margin-left: 0.7rem;
    color: ${({ theme }) => theme.colors.primary};
  }
`;
