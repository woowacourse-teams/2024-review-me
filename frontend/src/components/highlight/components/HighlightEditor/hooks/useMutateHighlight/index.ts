import { useMutation } from '@tanstack/react-query';

import { postHighlight } from '@/apis/highlight';
import { EditorAnswerMap } from '@/types';

export interface UseMutateHighlightProps {
  questionId: number;
  updateEditorAnswerMap: (editorAnswerMap: EditorAnswerMap) => void;
  resetHighlightMenu: () => void;
  handleErrorModal: (isError: boolean) => void;
}

const useMutateHighlight = ({
  questionId,
  handleErrorModal,
  updateEditorAnswerMap,
  resetHighlightMenu,
}: UseMutateHighlightProps) => {
  const mutation = useMutation({
    mutationFn: (newEditorAnswerMap: EditorAnswerMap) => postHighlight(newEditorAnswerMap, questionId),

    onSuccess: (_, variables: EditorAnswerMap) => {
      updateEditorAnswerMap(variables);
      resetHighlightMenu();
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
