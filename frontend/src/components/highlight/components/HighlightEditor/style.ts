import styled from '@emotion/styled';

export const HighlightEditor = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  padding: 1rem;
`;

export const SwitchButtonWrapper = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: end;

  width: 100%;
  margin-bottom: 1rem;
`;

export const SwitchModIcon = styled.img`
  width: 1.6rem;
  height: 1.6rem;
`;

export const HighlightText = styled.span<{ $isEditable: boolean }>`
  display: inline-block;
  color: ${({ $isEditable, theme }) => ($isEditable ? theme.colors.primary : theme.colors.gray)};
`;

export const AnswerList = styled.ul`
  list-style: disc;
  list-style-position: outside;
`;

export const AnswerListItem = styled.li`
  margin-bottom: 1rem;
  margin-left: 2rem;
  &::marker {
    margin: 0;
  }
`;
