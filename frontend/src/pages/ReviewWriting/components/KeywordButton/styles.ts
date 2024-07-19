import styled from '@emotion/styled';

export const KeywordButton = styled.button<{ isSelected: boolean }>`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  width: 30rem;
  height: 5rem;

  background-color: ${({ theme, isSelected }) => (isSelected ? '#E6E3F9' : theme.colors.white)};
  color: black;

  border: 0.2rem solid ${({ theme, isSelected }) => (isSelected ? theme.colors.primary : theme.colors.lightGray)};
  border-radius: 2rem;
  padding: 2rem;

  &:hover {
    background-color: ${({ theme, isSelected }) => (isSelected ? theme.colors.primaryHover : theme.colors.lightGray)};
  }
`;
