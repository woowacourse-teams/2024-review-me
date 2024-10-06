import { HIGHLIGHT_SPAN_CLASS_NAME, SENTENCE_BASIC_CLASS_NAME } from '@/constants';

import * as S from './style';
interface SentenceProps {
  isHighlight: boolean;
  text: string;
}

const Sentence = ({ isHighlight, text }: SentenceProps) => {
  const className = `${SENTENCE_BASIC_CLASS_NAME} ${isHighlight ? HIGHLIGHT_SPAN_CLASS_NAME : ''}`;
  return (
    <S.Sentence className={className} $isHighlight={isHighlight}>
      {text}
    </S.Sentence>
  );
};

export default Sentence;
