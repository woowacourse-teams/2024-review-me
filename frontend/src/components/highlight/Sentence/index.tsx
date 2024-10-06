import { HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';

import * as S from './style';
interface SentenceProps {
  isHighlight: boolean;
  text: string;
}

const Sentence = ({ isHighlight, text }: SentenceProps) => {
  return (
    <S.Sentence className={isHighlight ? HIGHLIGHT_SPAN_CLASS_NAME : ''} $isHighlight={isHighlight}>
      {text}
    </S.Sentence>
  );
};

export default Sentence;
