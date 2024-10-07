import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { EditorBlockData, Highlight } from '@/types';

import Sentence from '../Sentence';

interface EditorBlockProps {
  block: EditorBlockData;
  blockIndex: number;
}

const EditorBlock = ({ block, blockIndex }: EditorBlockProps) => {
  const { text, highlightList } = block;

  const renderSentenceList = () => {
    if (!highlightList.length) {
      return <Sentence text={text} spanIndex={0} />;
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
    const result: { text: string; highlightInfo: { start: number; end: number } | undefined }[] = [];
    let currentIndex = 0;

    highlightList.forEach(({ start, end }) => {
      if (currentIndex < start) {
        result.push({ highlightInfo: undefined, text: text.slice(currentIndex, start) });
      }
      result.push({ highlightInfo: { start, end }, text: text.slice(start, end + 1) });
      currentIndex = end + 1;
    });

    if (currentIndex < text.length) {
      result.push({ highlightInfo: undefined, text: text.slice(currentIndex) });
    }
    return result;
  };

  /**
   * 하이라이트 적용 여부를 반영한 Sentence 컴포넌트를 렌더링하는 함수
   */
  const renderStyledSentenceList = () => {
    const highlightedTextList = splitTextWithHighlightList({ text, highlightList });
    const key = `${EDITOR_BLOCK_CLASS_NAME}-${blockIndex}__span`;

    return (
      <>
        {highlightedTextList.map(({ text, highlightInfo }, i) => (
          <Sentence key={`${key}-${i}`} text={text} spanIndex={i} highlightInfo={highlightInfo} />
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
