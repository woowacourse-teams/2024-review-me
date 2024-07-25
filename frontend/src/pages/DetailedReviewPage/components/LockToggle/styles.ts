import styled from '@emotion/styled';

interface ButtonProps {
  $isActive: boolean;
}
export const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 9rem;
  height: 3rem;
  padding: 0.8rem 0.8rem;

  font-size: ${({ theme }) => theme.fontSize.small};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${(props) => (props.$isActive ? props.theme.colors.black : props.theme.colors.gray)};

  background-color: ${(props) => (props.$isActive ? props.theme.colors.white : 'transparent')};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  img {
    width: 1.4rem;
    height: 1.4rem;
    margin-right: 0.4rem;
    fill: ${(props) => (props.$isActive ? props.theme.colors.black : props.theme.colors.gray)};
  }
`;

export const LockToggle = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 20rem;
  height: 4rem;
  padding: 0.5rem 0.6rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;
