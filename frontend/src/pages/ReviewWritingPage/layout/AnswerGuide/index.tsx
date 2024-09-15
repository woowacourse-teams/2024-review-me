import { TEXT_ANSWER_LENGTH } from '@/pages/ReviewWritingPage/constants';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface AnswerGuideProps {
  question: ReviewWritingCardQuestion;
}

const AnswerGuide = ({ question }: AnswerGuideProps) => {
  /**
   * 객관식 문항의 최소,최대 개수에 대한 안내 문구
   */
  const multipleLGuideline = (() => {
    const { optionGroup } = question;

    // NOTE: 주관식일 경우의 안내 문구 처리
    if (!optionGroup) {
      const guideline = question.required
        ? `(최소 ${TEXT_ANSWER_LENGTH.min}자 ~ 최대 ${TEXT_ANSWER_LENGTH.max}자)`
        : `(최대 ${TEXT_ANSWER_LENGTH.max}자)`;
      return guideline;
    }

    // NOTE: 객관식일 경우의 안내 문구 처리
    const { minCount, maxCount } = optionGroup;
    //선택 질문
    if (!question.required) return `(${maxCount}개 이하)`;

    const isAllSelectAvailable = maxCount === optionGroup.options.length;

    if (!maxCount || isAllSelectAvailable) return `(최소 ${minCount}개 이상)`;
    if (minCount === maxCount) return `(${maxCount}개)`;

    return `(${minCount}개 ~ ${maxCount}개)`;
  })();
  return (
    <>
      {question.required ? (
        <S.QuestionRequiredMark>*</S.QuestionRequiredMark>
      ) : (
        <S.NotRequiredQuestionText>(선택)</S.NotRequiredQuestionText>
      )}
      <S.MultipleGuideline>{multipleLGuideline ?? ''}</S.MultipleGuideline>
    </>
  );
};

export default AnswerGuide;
