import { useRecoilValue } from 'recoil';

import { answerMapAtom, cardSectionListSelector } from '@/recoil';
import { ReviewWritingCardQuestion } from '@/types';

interface UseCancelAnsweredCategoryProps {
  question: ReviewWritingCardQuestion;
}
/**
 * 선택을 해제하려는 꼬리 질문에 이미 작성한 답변이 있는 지 여부를 확인하는 훅
 * @param question
 */
const useCheckTailQuestionAnswer = ({ question }: UseCancelAnsweredCategoryProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const answerMap = useRecoilValue(answerMapAtom);

  const isCategoryQuestion = question.questionId === cardSectionList[0].questions[0].questionId;
  // 이미 답변을 작성한 카테고리를 해제하는 경우
  /**
   * 카테고리 항목 선택일때,  optionId에 해당하는 카테고리 찾기
   */
  const getCategoryByOptionId = (categoryOptionId: number) => {
    return cardSectionList.filter((section) => section.onSelectedOptionId === categoryOptionId)[0];
  };

  /**
   * categoryOptionId로 찾은 카테고리에 답변이 달려있는 지 여부
   * 답이 있기만 하다면 true
   */
  const isSelectedCategoryAnswer = (categoryOptionId: number) => {
    if (!answerMap) return false;
    // 선택한 객관식에 해당하는 카테고리의 질문들 가져오기
    const targetCategoryQuestionList = getCategoryByOptionId(categoryOptionId);

    if (!targetCategoryQuestionList) return false;
    //카테고리에 유효한 답변이 있는 지 판단
    const questionIdList = targetCategoryQuestionList.questions.map((question) => question.questionId);
    const questionId = questionIdList.find((id) => answerMap.has(id));

    if (!questionId) return;

    const answer = answerMap.get(questionId);

    return !!answer?.selectedOptionIds?.length || !!answer?.text?.length;
  };

  /**
   * 강점 카테고리 객관식 문항에서 선택을 해제하려는 강점에 대한 꼬리 질문에 이미 작성된 답변이 있는 지 여부
   * @param optionId : 강점 카테고리 객관식 문항에서 선택을 해제하려는 문항의 optionId
   */
  const isAnsweredTailQuestion = (optionId: number) => {
    //1. 강점 카테고리 문항이 아닌 경우
    if (!isCategoryQuestion) return false;
    //2. 강점 카테고리 문항일 때, 선택을 해제하려는 강점에 대한 꼬리 질문에 이미 작성된 답변이 있는 지 여부
    return isSelectedCategoryAnswer(optionId);
  };

  return {
    isAnsweredTailQuestion,
  };
};

export default useCheckTailQuestionAnswer;
