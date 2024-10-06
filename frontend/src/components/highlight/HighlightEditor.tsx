import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { useHighlight } from '@/hooks';

import EditorBlock from './EditorBlock';

interface HighlightEditorProps {
  text: string;
}

const HighlightEditor = ({ text }: HighlightEditorProps) => {
  const { blockList, handleClickHighlight, handleClickHighlightRemover } = useHighlight({ text });

  return (
    <div className="highlight-editor">
      {blockList.map((block, index) => (
        <EditorBlock key={`${EDITOR_BLOCK_CLASS_NAME}-${index}`} block={block} blockIndex={index} />
      ))}
      <button onClick={handleClickHighlight}>Add Highlight</button>
      <button onClick={handleClickHighlightRemover}>Delete Highlight</button>
    </div>
  );
};

export default HighlightEditor;
