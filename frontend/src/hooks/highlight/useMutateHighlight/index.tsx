import { useMutation } from '@tanstack/react-query';
import { useEffect } from 'react';

import { postHighlight } from '@/apis/highlight';
import { EditorAnswerMap } from '@/types';

export interface UseMutateHighlightProps {
  questionId: number;
  updateEditorAnswerMap: (editorAnswerMap: EditorAnswerMap) => void;
  resetHighlightButton: () => void;
  handleErrorModal: (isError: boolean) => void;
}

const useMutateHighlight = ({
  questionId,
  handleErrorModal,
  updateEditorAnswerMap,
  resetHighlightButton,
}: UseMutateHighlightProps) => {
  const mutation = useMutation({
    mutationFn: (newEditorAnswerMap: EditorAnswerMap) => postHighlight(newEditorAnswerMap, questionId),

    onSuccess: (_, variables: EditorAnswerMap) => {
      updateEditorAnswerMap(variables);
      resetHighlightButton();
      // 토스트 모달 지우기
      handleErrorModal(false);
    },
    onError: (error) => {
      //토스트 모달 띄움
      handleErrorModal(true);
    },
  });

  return mutation;
};

export default useMutateHighlight;
