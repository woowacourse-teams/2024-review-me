import styled from '@emotion/styled';

interface SentenceProps {
  $isHighlight: boolean;
}
export const Sentence = styled.span<SentenceProps>`
  line-height: 1.5;
  color: ${(props) => (props.$isHighlight ? props.theme.colors.white : 'inherit')};
  background-color: ${(props) => (props.$isHighlight ? props.theme.colors.primary : 'transparent')};
`;
