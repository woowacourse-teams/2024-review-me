import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import useUpdateReviewerAnswer from '../useUpdateReviewerAnswer';

interface UseUpdateMultipleChoiceAnswerProps {
  question: ReviewWritingCardQuestion;
}
/**
  객관식에서 선택된 문항을 바탕으로 answerMap, answerValidationMap의 상태를 업데이트하는 훅
 */
const useUpdateMultipleChoiceAnswer = ({ question }: UseUpdateMultipleChoiceAnswerProps) => {
  const { updateAnswerMap, updateAnswerValidationMap } = useUpdateReviewerAnswer();

  /**
   * 객관식 문항의 최소, 최대 선택 개수를 지킨 유효한 답변인지 여부를 판단
   */
  const isValidatedChoice = (newSelectedOptionList: number[]) => {
    if (!question.optionGroup) return false;

    const { minCount, maxCount } = question.optionGroup;
    const { length } = newSelectedOptionList;

    return length >= minCount && length <= maxCount;
  };

  const updateAnswerState = (newSelectedOptionList: number[]) => {
    // 유효한 선택(=객관식 문항의 최소,최대 개수를 지켰을 경우)인지에 따라 answer 변경
    const isValidatedAnswer = isValidatedChoice(newSelectedOptionList);
    const isNotRequiredEmptyAnswer = !question.required && newSelectedOptionList.length === 0;

    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: isValidatedAnswer ? newSelectedOptionList : [],
      text: null,
    };

    updateAnswerMap(newAnswer);
    updateAnswerValidationMap(newAnswer, isValidatedAnswer || isNotRequiredEmptyAnswer);
  };

  return {
    updateAnswerState,
  };
};

export default useUpdateMultipleChoiceAnswer;
