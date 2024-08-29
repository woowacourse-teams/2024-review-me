import { LongReviewItem } from '@/components';
import { useTextAnswer } from '@/hooks';
import { ReviewWritingCardQuestion } from '@/types';

import MultipleChoiceAnswer from '../MultipleChoiceAnswer';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
}
/**
 * 하나의 질문과 그에 대한 답을 관리
 */

const QnABox = ({ question }: QnABoxProps) => {
  /**
   * 객관식 문항의 최소,최대 개수에 대한 안내 문구
   */
  const multipleLGuideline = (() => {
    const { optionGroup } = question;
    if (!optionGroup) return;

    const { minCount, maxCount } = optionGroup;

    const isAllSelectAvailable = maxCount === optionGroup.options.length;
    if (!maxCount || isAllSelectAvailable) return `(최소 ${minCount}개 이상)`;

    return `(${minCount}개 ~ ${maxCount}개)`;
  })();

  const { textAnswer, handleTextAnswerChange, TEXT_ANSWER_LENGTH } = useTextAnswer({
    question,
  });

  return (
    <S.QnASection>
      <S.QuestionTitle>
        {question.content}
        {question.required ? <S.QuestionRequiredMark>*</S.QuestionRequiredMark> : <span> (선택) </span>}
        <S.MultipleGuideline>{multipleLGuideline ?? ''}</S.MultipleGuideline>
      </S.QuestionTitle>
      {question.guideline && <S.QuestionGuideline>{question.guideline}</S.QuestionGuideline>}
      {/*객관식*/}
      {question.questionType === 'CHECKBOX' && <MultipleChoiceAnswer question={question} />}
      {/*서술형*/}
      {question.questionType === 'TEXT' && (
        <LongReviewItem
          initialValue={textAnswer}
          minLength={TEXT_ANSWER_LENGTH.min}
          maxLength={TEXT_ANSWER_LENGTH.max}
          handleTextareaChange={handleTextAnswerChange}
          required={question.required}
        />
      )}
    </S.QnASection>
  );
};

export default QnABox;
