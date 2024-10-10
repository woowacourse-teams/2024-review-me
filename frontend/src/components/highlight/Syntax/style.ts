import styled from '@emotion/styled';

interface SyntaxProps {
  $isHighlight: boolean;
}
export const Syntax = styled.span<SyntaxProps>`
  line-height: 1.5;
  color: ${(props) => (props.$isHighlight ? props.theme.colors.white : 'inherit')};
  background-color: ${(props) => (props.$isHighlight ? props.theme.colors.primary : 'transparent')};
`;
