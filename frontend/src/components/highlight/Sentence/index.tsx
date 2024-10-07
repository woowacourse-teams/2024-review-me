import { HIGHLIGHT_SPAN_CLASS_NAME, SENTENCE_BASIC_CLASS_NAME } from '@/constants';

import * as S from './style';
interface SentenceProps {
  text: string;
  spanIndex: number;
  highlightInfo?: { start: number; end: number };
}

const Sentence = ({ text, spanIndex, highlightInfo }: SentenceProps) => {
  const className = `${SENTENCE_BASIC_CLASS_NAME} ${highlightInfo ? HIGHLIGHT_SPAN_CLASS_NAME : ''}`;
  return (
    <>
      {highlightInfo ? (
        <S.Sentence
          className={className}
          $isHighlight={true}
          data-index={spanIndex}
          data-highlight-start={highlightInfo.start}
          data-highlight-end={highlightInfo.end}
        >
          {text}
        </S.Sentence>
      ) : (
        <S.Sentence className={className} $isHighlight={false} data-index={spanIndex}>
          {text}
        </S.Sentence>
      )}
    </>
  );
};

export default Sentence;
