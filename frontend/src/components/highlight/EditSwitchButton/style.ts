import styled from '@emotion/styled';

interface EditorSwitchProps {
  $isEditable: boolean;
}
export const EditSwitchButton = styled.button<EditorSwitchProps>`
  cursor: pointer;

  width: 4rem;
  padding: 0.5rem;

  background-color: ${({ theme, $isEditable }) => ($isEditable ? theme.colors.primary : theme.colors.gray)};
  border-radius: 3.4rem;

  transition: background-color 0.3s ease;
`;

export const Circle = styled.div<EditorSwitchProps>`
  transform: translateX(${({ $isEditable }) => ($isEditable ? 0 : '1.5rem')});

  width: 1.5rem;
  height: 1.5rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 50%;

  transition: transform 0.4s ease-in-out;
`;
