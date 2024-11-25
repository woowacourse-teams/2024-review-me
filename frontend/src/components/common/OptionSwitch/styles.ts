import styled from '@emotion/styled';

import { OptionSwitchStyleProps } from './index';

export const OptionSwitchContainer = styled.ul`
  cursor: pointer;

  display: flex;
  justify-content: space-between;

  width: 20rem;
  height: 4.4rem;
  padding: 0.7rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  margin-top: 0.9rem;

  @media screen and (max-width: 530px) {
    width: 100%;
  }
`;

export const CheckboxWrapper = styled.li<OptionSwitchStyleProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 50%;
  height: 100%;

  background-color: ${({ $isChecked, theme }) => ($isChecked ? theme.colors.white : theme.colors.lightGray)};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  transition: background-color 0.2s ease-out;

  &:hover {
    background-color: ${({ $isChecked, theme }) => ($isChecked ? theme.colors.white : theme.colors.lightPurple)};
  }
`;

export const CheckboxButton = styled.button<OptionSwitchStyleProps>`
  user-select: none;
  font-size: 1.4rem;
  color: ${({ $isChecked, theme }) => ($isChecked ? theme.colors.primary : theme.colors.black)};
`;
