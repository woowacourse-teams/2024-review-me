import { HIGHLIGHT_SPAN_CLASS_NAME, SENTENCE_BASIC_CLASS_NAME } from '@/constants';

import * as S from './style';
interface SentenceProps {
  isHighlight: boolean;
  text: string;
  index: number;
}

const Sentence = ({ isHighlight, text, index }: SentenceProps) => {
  const className = `${SENTENCE_BASIC_CLASS_NAME} ${isHighlight ? HIGHLIGHT_SPAN_CLASS_NAME : ''}`;
  return (
    <S.Sentence className={className} $isHighlight={isHighlight} data-index={index}>
      {text}
    </S.Sentence>
  );
};

export default Sentence;
