import { useEffect } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import { answerMapAtom, answerValidationMapAtom, questionListSelector } from '@/recoil';
import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

/**
 * questionListSelector(=리뷰 작성 페이지에서 리뷰이가 작성해야하는 질문지)가 변경되었을때, 이에 맞추어서 답변(answerMap)과 답변들의 유효성 여부(answerValidationMap)을 변경하는 훅
 */
const useUpdateDefaultAnswers = () => {
  const questionList = useRecoilValue(questionListSelector);
  // NOTE : answerMap - 질문에 대한 답변들 , number : questionId
  const [answerMap, setAnswerMap] = useRecoilState(answerMapAtom);
  // NOTE : answerValidationMap  -질문의 단볍들의 유효성 여부 ,number: questionId
  const [answerValidationMap, setAnswerValidationMap] = useRecoilState(answerValidationMapAtom);
  /* NOTE: 질문 변경 시, answerMap 변경 케이스 정리
    case1. 이전에 작성한 답이 있는 경우 : answerMap , answerValidation에 이를 반영
    case2. 이전에 작성한 답이 없는 경우 : answerMap, answerValidation에 기본값 설정
      - 서술형:'', 객관식 :[]*/
  /**
   * 질문지 변경 시, 질문지에 맞는 새로운 answerMap, answerValidationMap을 반환하는 함수
   */
  const makeNewAnswerAndValidationMaps = () => {
    const newAnswerMap: Map<number, ReviewWritingAnswer> = new Map();
    const newAnswerValidationMap: Map<number, boolean> = new Map();
    questionList?.forEach((section) => {
      section.questions.forEach((question) => {
        updateNewAnswerMaps({ newAnswerMap, question });
        updateNewAnswerValidationMap({ newAnswerValidationMap, question });
      });
    });

    return { newAnswerMap, newAnswerValidationMap };
  };
  interface UpdateAnswerValidationMapParams {
    newAnswerValidationMap: Map<number, boolean>;
    question: ReviewWritingCardQuestion;
  }
  /**
   * 변경된 질문지에 따라 생성될 새로운 answerValidationMap의 값을 변경하는 함수
   */
  const updateNewAnswerValidationMap = ({ newAnswerValidationMap, question }: UpdateAnswerValidationMapParams) => {
    newAnswerValidationMap.set(
      question.questionId,
      answerValidationMap?.get(question.questionId) ?? !question.required,
    );
  };

  interface UpdateAnswerMapParams {
    newAnswerMap: Map<number, ReviewWritingAnswer>;
    question: ReviewWritingCardQuestion;
  }
  /**
   * 변경된 질문지에 따라 생성될 새로운 answerMap의 값을 변경하는 함수
   */
  const updateNewAnswerMaps = ({ newAnswerMap, question }: UpdateAnswerMapParams) => {
    const answer = answerMap?.get(question.questionId);
    if (answer) {
      reflectExistingAnswerInMaps({ answer, newAnswerMap, question });
      return;
    }
    setInitialAnswerMap({ newAnswerMap, question });
  };
  /**
   * 변경된 질문지에 대해 이전에 작성한 답변이 없는 경우, 답변 유형(객관식/서술형)에 따라 답변에 기본값을 설정하는 함수
   */
  const setInitialAnswerMap = ({ newAnswerMap, question }: UpdateAnswerMapParams) => {
    newAnswerMap.set(question.questionId, {
      questionId: question.questionId,
      selectedOptionIds: question.questionType === 'CHECKBOX' ? [] : null,
      text: question.questionType === 'TEXT' ? '' : null,
    });
  };
  interface ReflectExistingAnswerInMapsParams extends UpdateAnswerMapParams {
    answer: ReviewWritingAnswer;
  }
  /**
   * 변경된 질문지에 대해 이전에 작성한 답변이 있는 경우, 답변 유형(객관식/서술형)에 따라 답변에 기본값을 설정하는 함수
   */
  const reflectExistingAnswerInMaps = ({ answer, newAnswerMap, question }: ReflectExistingAnswerInMapsParams) => {
    // 객관식
    if (question.questionType === 'CHECKBOX') {
      newAnswerMap.set(question.questionId, {
        questionId: question.questionId,
        selectedOptionIds: answer.selectedOptionIds ?? [],
        text: null,
      });
    }
    // 서술형
    if (question.questionType === 'TEXT') {
      newAnswerMap.set(question.questionId, {
        questionId: question.questionId,
        selectedOptionIds: null,
        text: answer.text ?? '',
      });
    }
  };

  useEffect(() => {
    const { newAnswerMap, newAnswerValidationMap } = makeNewAnswerAndValidationMaps();
    setAnswerMap(newAnswerMap);
    setAnswerValidationMap(newAnswerValidationMap);
  }, [questionList]);
};

export default useUpdateDefaultAnswers;
