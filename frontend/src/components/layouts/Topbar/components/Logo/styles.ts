import styled from '@emotion/styled';

import media from '@/utils/media';

export const Logo = styled.div`
  line-height: 8rem;
  text-align: center;

  span {
    font-size: 3rem;
    font-weight: ${({ theme }) => theme.fontWeight.bolder};
    letter-spacing: 0.7rem;

    ${media.small} {
      font-size: 2.8rem;
    }

    ${media.xSmall} {
      font-size: 2.6rem;
    }
  }

  span:last-child {
    margin-left: 0.7rem;
    color: ${({ theme }) => theme.colors.primary};
  }
`;
