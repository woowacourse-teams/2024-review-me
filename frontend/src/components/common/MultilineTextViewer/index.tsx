import * as S from './styles';

interface MultilineTextViewerProps {
  text: string;
}

const MultilineTextViewer = ({ text }: MultilineTextViewerProps) => {
  return (
    <>
      {text.split('\n').map((line, index) => (
        <S.MultilineText key={index}>
          {line}
          <br />
        </S.MultilineText>
      ))}
    </>
  );
};

export default MultilineTextViewer;
