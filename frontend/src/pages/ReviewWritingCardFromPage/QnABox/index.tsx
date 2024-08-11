import { CheckboxItem, LongReviewItem } from '@/components';
import { useMultipleChoice, useTextAnswer } from '@/hooks';
import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
}

const QnABox = ({ question, updateAnswerMap }: QnABoxProps) => {
  const { isOpenLimitGuide, handleCheckboxChange, isSelectedCheckbox } = useMultipleChoice({
    question,
    updateAnswerMap,
  });

  const { textAnswer, handelTextAnswerChange, TEXT_ANSWER_LENGTH } = useTextAnswer({ question, updateAnswerMap });

  return (
    <S.QnASection>
      <S.QuestionTitle>
        {question.content}
        {question.required && <S.QuestionRequiredMark>*</S.QuestionRequiredMark>}
      </S.QuestionTitle>
      {question.guideline && <S.QuestionGuideline>{question.guideline}</S.QuestionGuideline>}
      {/*객관식*/}
      {question.questionType === 'CHECKBOX' &&
        question.optionGroup?.options.map((option) => (
          <CheckboxItem
            key={option.optionId}
            id={option.optionId.toString()}
            isChecked={isSelectedCheckbox(option.optionId)}
            isDisabled={false}
            label={option.content}
            onChange={handleCheckboxChange}
          />
        ))}
      {isOpenLimitGuide && (
        <S.LimitGuideMessage>😮 최대 {question.optionGroup?.maxCount}개까지 선택가능해요.</S.LimitGuideMessage>
      )}
      {/*서술형*/}
      {question.questionType === 'TEXT' && (
        <LongReviewItem
          initialValue={textAnswer}
          minLength={TEXT_ANSWER_LENGTH.min}
          maxLength={TEXT_ANSWER_LENGTH.max}
          handleTextareaChange={handelTextAnswerChange}
        />
      )}
    </S.QnASection>
  );
};

export default QnABox;
