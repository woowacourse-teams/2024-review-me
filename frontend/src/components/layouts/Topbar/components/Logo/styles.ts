import styled from '@emotion/styled';

export const Logo = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;

  img {
    width: 4rem;
    height: 4rem;
  }
`;

export const LogoText = styled.div`
  line-height: 8rem;
  text-align: center;

  span {
    font-size: 3rem;
    font-weight: ${({ theme }) => theme.fontWeight.bolder};
    letter-spacing: 0.7rem;
  }

  span:last-child {
    margin-left: 0.7rem;
    color: ${({ theme }) => theme.colors.primary};
  }
`;
