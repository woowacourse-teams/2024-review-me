import styled from '@emotion/styled';

import media from '@/utils/media';

export const OptionSwitchContainer = styled.div`
  cursor: pointer;

  display: flex;
  justify-content: space-between;

  width: 15rem;
  height: 4.5rem;
  padding: 0.7rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.small} {
    height: 3.5rem;
    font-size: 1.2rem;
  }
`;

export const CheckboxWrapper = styled.div<{ isChecked: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 50%;
  height: 100%;

  background-color: ${({ isChecked, theme }) => (isChecked ? theme.colors.white : theme.colors.lightGray)};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  transition: background-color 0.2s ease-out;
`;

export const CheckboxLabel = styled.span<{ isChecked: boolean }>`
  user-select: none;
  font-size: 1.2rem;
  color: ${({ isChecked, theme }) => (isChecked ? theme.colors.primary : theme.colors.black)};
`;
