import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { EditorBlockData, Highlight } from '@/types';

import Syntax from '../Syntax';

interface EditorBlockProps {
  block: EditorBlockData;
  blockIndex: number;
}

const EditorBlock = ({ block, blockIndex }: EditorBlockProps) => {
  const { text, highlightList } = block;

  const renderSentenceList = () => {
    if (!highlightList.length) {
      return <Syntax text={text} spanIndex={0} />;
    }
    return renderStyledSentenceList();
  };
  interface SplitTextWithHighlightListParams {
    text: string;
    highlightList: Highlight[];
  }

  /**
   * 하이라이트에 따라, 블록의 글자를 하이라이트 적용되는 부분과 그렇지 않은 부분으로 나누는 함수
   */
  const splitTextWithHighlightList = ({ text, highlightList }: SplitTextWithHighlightListParams) => {
    const result: { text: string; highlightInfo: Highlight | undefined }[] = [];
    let currentIndex = 0;

    highlightList.forEach(({ startIndex, endIndex }) => {
      if (currentIndex < startIndex) {
        result.push({ highlightInfo: undefined, text: text.slice(currentIndex, startIndex) });
      }
      result.push({ highlightInfo: { startIndex, endIndex }, text: text.slice(startIndex, endIndex + 1) });
      currentIndex = endIndex + 1;
    });

    if (currentIndex < text.length) {
      result.push({ highlightInfo: undefined, text: text.slice(currentIndex) });
    }
    return result;
  };

  /**
   * 하이라이트 적용 여부를 반영한 Syntax 컴포넌트를 렌더링하는 함수
   */
  const renderStyledSentenceList = () => {
    const highlightedTextList = splitTextWithHighlightList({ text, highlightList });
    const key = `${EDITOR_BLOCK_CLASS_NAME}-${blockIndex}__span`;

    return (
      <>
        {highlightedTextList.map(({ text, highlightInfo }, i) => (
          <Syntax key={`${key}-${i}`} text={text} spanIndex={i} highlightInfo={highlightInfo} />
        ))}
      </>
    );
  };

  return (
    <div className={EDITOR_BLOCK_CLASS_NAME} data-index={blockIndex}>
      {renderSentenceList()}
    </div>
  );
};

export default EditorBlock;
