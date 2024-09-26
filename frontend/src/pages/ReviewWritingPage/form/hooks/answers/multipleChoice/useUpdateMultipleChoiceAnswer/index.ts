import { useUpdateReviewerAnswer } from '@/pages/ReviewWritingPage/form/hooks';
import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

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

    const isMinSatisfied = length >= minCount;
    const isMaxSatisfied = length <= maxCount;

    // 선택 질문 - 최대 선택 이하
    if (!question.required) {
      return isMaxSatisfied;
    }

    //필수 질문 - 최소 선택 이상 최대 선택 이하
    return isMinSatisfied && isMaxSatisfied;
  };

  const updateAnswerState = (newSelectedOptionList: number[]) => {
    // 유효한 선택인지에 따라 answer 변경
    const isValidatedAnswer = isValidatedChoice(newSelectedOptionList);

    const newAnswer: ReviewWritingAnswer = {
      questionId: question.questionId,
      selectedOptionIds: isValidatedAnswer ? newSelectedOptionList : [],
      text: null,
    };

    updateAnswerMap(newAnswer);
    updateAnswerValidationMap(newAnswer, isValidatedAnswer);
  };

  return {
    updateAnswerState,
  };
};

export default useUpdateMultipleChoiceAnswer;
