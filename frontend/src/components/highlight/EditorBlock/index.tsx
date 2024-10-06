import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { EditorBlockData } from '@/types';
import { splitTextWithHighlightList } from '@/utils';

import Sentence from '../Sentence';

interface EditorBlockProps {
  block: EditorBlockData;
  blockIndex: number;
}

const EditorBlock = ({ block, blockIndex }: EditorBlockProps) => {
  const { text, highlightList } = block;

  const renderSentenceList = () => {
    if (!highlightList.length) {
      return <Sentence isHighlight={false} text={text} />;
    }
    return renderStyledSentenceList();
  };

  /**
   * 하이라이트 적용 여부를 반영한 Sentence 컴포넌트를 렌더링하는 함수
   */
  const renderStyledSentenceList = () => {
    const highlightedTextList = splitTextWithHighlightList({ text, highlightList });
    const key = `${EDITOR_BLOCK_CLASS_NAME}-${blockIndex}__span`;

    return (
      <>
        {highlightedTextList.map(({ isHighlight, text }, i) => (
          <Sentence key={`${key}-${i}`} isHighlight={isHighlight} text={text} />
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
