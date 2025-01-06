import * as S from './styles';

interface MultilineTextViewerProps {
  text: string;
}

const MultilineTextViewer = ({ text }: MultilineTextViewerProps) => {
  return (
    <S.MultilineTextContainer>
      {text.split('\n').map((line, index) => (
        <S.MultilineText key={index}>
          {line}
          <br />
        </S.MultilineText>
      ))}
    </S.MultilineTextContainer>
  );
};

export default MultilineTextViewer;
