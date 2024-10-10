import { HIGHLIGHT_SPAN_CLASS_NAME, SYNTAX_BASIC_CLASS_NAME } from '@/constants';
import { HighlightRange } from '@/types';

import * as S from './style';
interface SyntaxProps {
  text: string;
  spanIndex: number;
  highlightRange?: HighlightRange;
}

const Syntax = ({ text, spanIndex, highlightRange }: SyntaxProps) => {
  const className = `${SYNTAX_BASIC_CLASS_NAME} ${highlightRange ? HIGHLIGHT_SPAN_CLASS_NAME : ''}`;
  return (
    <>
      {highlightRange ? (
        <S.Syntax
          className={className}
          $isHighlight={true}
          data-index={spanIndex}
          data-highlight-start={highlightRange.startIndex}
          data-highlight-end={highlightRange.endIndex}
        >
          {text}
        </S.Syntax>
      ) : (
        <S.Syntax className={className} $isHighlight={false} data-index={spanIndex}>
          {text}
        </S.Syntax>
      )}
    </>
  );
};

export default Syntax;
