import { HIGHLIGHT_SPAN_CLASS_NAME, SYNTAX_BASIC_CLASS_NAME } from '@/constants';
import { Highlight } from '@/types';

import * as S from './style';
interface SyntaxProps {
  text: string;
  spanIndex: number;
  highlightInfo?: Highlight;
}

const Syntax = ({ text, spanIndex, highlightInfo }: SyntaxProps) => {
  const className = `${SYNTAX_BASIC_CLASS_NAME} ${highlightInfo ? HIGHLIGHT_SPAN_CLASS_NAME : ''}`;
  return (
    <>
      {highlightInfo ? (
        <S.Syntax
          className={className}
          $isHighlight={true}
          data-index={spanIndex}
          data-highlight-start={highlightInfo.startIndex}
          data-highlight-end={highlightInfo.endIndex}
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
