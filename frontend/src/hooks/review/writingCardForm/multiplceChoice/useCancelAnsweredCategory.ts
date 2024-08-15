import { useRecoilValue } from 'recoil';

import { answerMapAtom, cardSectionListSelector } from '@/recoil';
import { ReviewWritingCardQuestion } from '@/types';

interface UseCancelAnsweredCategoryProps {
  question: ReviewWritingCardQuestion;
}
const useCancelAnsweredCategory = ({ question }: UseCancelAnsweredCategoryProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const answerMap = useRecoilValue(answerMapAtom);
  const isCategoryQuestion = () => {
    return question.questionId === cardSectionList[0].questions[0].questionId;
  };
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

    return !!answer?.selectedOptionIds || !!answer?.text;
  };

  /**
   * 해제하기 위해 카테고리 문항을 선택한 경우, 이미 이에 대해 답변을 했는 지 여부
   * @param optionId : 문항의 optionId
   */
  const isAnsweredCategoryChanged = (optionId: number) => {
    if (!isCategoryQuestion) return false;
    return isSelectedCategoryAnswer(optionId);
  };

  return {
    isAnsweredCategoryChanged,
  };
};

export default useCancelAnsweredCategory;
