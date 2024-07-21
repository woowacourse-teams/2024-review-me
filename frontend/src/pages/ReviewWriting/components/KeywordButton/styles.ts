import styled from '@emotion/styled';

export const KeywordButton = styled.button<{ isSelected: boolean }>`
  display: flex;
  align-items: center;
  justify-content: flex-start;

  width: 30rem;
  height: 5rem;
  padding: 2rem;

  color: black;

  background-color: ${({ theme, isSelected }) => (isSelected ? '#E6E3F9' : theme.colors.white)};
  border: 0.2rem solid ${({ theme, isSelected }) => (isSelected ? theme.colors.primary : theme.colors.lightGray)};
  border-radius: 2rem;

  &:hover {
    background-color: ${({ theme, isSelected }) => (isSelected ? theme.colors.primaryHover : theme.colors.lightGray)};
  }
`;
