import { useMutation } from '@tanstack/react-query';

import { postHighlight } from '@/apis/highlight';
import { LOCAL_STORAGE_KEY } from '@/constants';
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
    onMutate: () => {
      if (mutation.isPending) return;
    },
    onSuccess: (_, variables: EditorAnswerMap) => {
      updateEditorAnswerMap(variables);
      resetHighlightMenu();
      // 토스트 모달 지우기
      handleErrorModal(false);
      localStorage.removeItem(LOCAL_STORAGE_KEY.isHighlightError);
    },
    onError: (error) => {
      //토스트 모달 띄움
      handleErrorModal(true);
      // fallback 실행으로 인한,isEditable 상태 초기화 막음
      localStorage.setItem(LOCAL_STORAGE_KEY.isHighlightError, 'true');
    },
  });

  return mutation;
};

export default useMutateHighlight;
