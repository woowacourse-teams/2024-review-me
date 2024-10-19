import { EDITOR_LINE_CLASS_NAME } from '@/constants';
import { EditorLine, HighlightRange } from '@/types';

import Syntax from '../Syntax';
interface EditorLineBlockProps {
  line: EditorLine;
  lineIndex: number;
}

const EditorLineBlock = ({ line, lineIndex }: EditorLineBlockProps) => {
  const { text, highlightList } = line;

  const renderSentenceList = () => {
    if (!highlightList.length) {
      return <Syntax text={text} spanIndex={0} />;
    }
    return renderStyledSentenceList();
  };
  interface SplitTextWithHighlightListParams {
    text: string;
    highlightList: HighlightRange[];
  }

  /**
   * 하이라이트에 따라, 블록의 글자를 하이라이트 적용되는 부분과 그렇지 않은 부분으로 나누는 함수
   */
  const splitTextWithHighlightList = ({ text, highlightList }: SplitTextWithHighlightListParams) => {
    const result: { text: string; highlightRange: HighlightRange | undefined }[] = [];
    let currentIndex = 0;

    highlightList.forEach(({ startIndex, endIndex }) => {
      if (currentIndex < startIndex) {
        result.push({ highlightRange: undefined, text: text.slice(currentIndex, startIndex) });
      }
      result.push({ highlightRange: { startIndex, endIndex }, text: text.slice(startIndex, endIndex + 1) });
      currentIndex = endIndex + 1;
    });

    if (currentIndex < text.length) {
      result.push({ highlightRange: undefined, text: text.slice(currentIndex) });
    }
    return result;
  };

  /**
   * 하이라이트 적용 여부를 반영한 Syntax 컴포넌트를 렌더링하는 함수
   */
  const renderStyledSentenceList = () => {
    const highlightedTextList = splitTextWithHighlightList({ text, highlightList });
    const key = `${EDITOR_LINE_CLASS_NAME}-${lineIndex}__span`;

    return (
      <>
        {highlightedTextList.map(({ text, highlightRange }, i) => (
          <Syntax key={`${key}-${i}`} text={text} spanIndex={i} highlightRange={highlightRange} />
        ))}
      </>
    );
  };

  return (
    <p className={EDITOR_LINE_CLASS_NAME} data-index={lineIndex}>
      {renderSentenceList()}
    </p>
  );
};

export default EditorLineBlock;
