import { useRef } from 'react';

import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import useRequestCodeParam from '@/hooks/useReviewRequestCodeParam';
import { ReviewAnswerResponseData } from '@/types';

import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightMenu from '../HighlightMenu';
import Tooltip from '../Tooltip';

import { useHighlight, useCheckHighlight, useEditableState, useHighlightEventListener } from './hooks';
import useHighlightMenuPosition from './hooks/useHighlightMenuPosition';
import * as S from './style';

export interface HighlightEditorProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
}

const HighlightEditor = ({ questionId, answerList }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);
  const { reviewRequestCode } = useRequestCodeParam();
  const { isEditable, handleEditToggleButton } = useEditableState();

  const { highlightArea, checkHighlight } = useCheckHighlight();

  const { menuPosition, updateHighlightMenuPositionByDrag, resetHighlightMenuPosition } = useHighlightMenuPosition({
    editorRef,
    isEditable,
  });

  const { editorAnswerMap, addHighlightByDrag, removeHighlightByDrag } = useHighlight({
    reviewRequestCode,
    questionId,
    answerList,
    resetHighlightMenuPosition,
  });

  useHighlightEventListener({
    isEditable,
    updateHighlightMenuPositionByDrag,
    resetHighlightMenuPosition,
    checkHighlight,
  });

  return (
    <S.HighlightEditor ref={editorRef}>
      <S.SwitchButtonWrapper>
        <S.HighlightText>형광펜</S.HighlightText>
        <Tooltip />
        <EditSwitchButton isEditable={isEditable} handleEditToggleButton={handleEditToggleButton} />
      </S.SwitchButtonWrapper>
      <S.AnswerList>
        {[...editorAnswerMap.values()].map(({ answerId, answerIndex, lineList }) => (
          <S.AnswerListItem
            className={EDITOR_ANSWER_CLASS_NAME}
            key={answerId}
            data-answer={`${answerId}-${answerIndex}`}
          >
            {lineList.map((line, index) => (
              <EditorLineBlock key={`${EDITOR_LINE_CLASS_NAME}-${index}`} line={line} lineIndex={index} />
            ))}
          </S.AnswerListItem>
        ))}
      </S.AnswerList>
      {isEditable && menuPosition && (
        <HighlightMenu
          position={menuPosition}
          highlightArea={highlightArea}
          addHighlightByDrag={addHighlightByDrag}
          removeHighlightByDrag={removeHighlightByDrag}
        />
      )}
    </S.HighlightEditor>
  );
};

export default HighlightEditor;
