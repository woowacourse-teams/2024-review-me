import styled from '@emotion/styled';

interface SyntaxProps {
  $isHighlight: boolean;
}
export const Syntax = styled.span<SyntaxProps>`
  cursor: ${({ $isHighlight }) => ($isHighlight ? 'pointer' : 'auto')};
  line-height: 1.5;
  background-color: ${(props) => (props.$isHighlight ? props.theme.colors.highlight : 'transparent')};
`;
