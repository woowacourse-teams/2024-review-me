import { CheckboxItem, LongReviewItem } from '@/components';
import { useMultipleChoice, useTextAnswer, useUpdateReviewerAnswer } from '@/hooks';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
}
/**
 * 하나의 질문과 그에 대한 답을 관리
 */

const QnABox = ({ question }: QnABoxProps) => {
  const { updateAnswerMap, updateAnswerValidationMap } = useUpdateReviewerAnswer();

  const handleModalOpen = (isOpen: boolean) => {
    isOpen ? openModal(MODAL_KEY.confirm) : closeModal(MODAL_KEY.confirm);
  };

  const { isOpenLimitGuide, multipleLGuideline, handleCheckboxChange, isSelectedCheckbox, unCheckTargetOption } =
    useMultipleChoice({
      question,
      handleModalOpen,
    });

  const { textAnswer, handleTextAnswerChange, TEXT_ANSWER_LENGTH } = useTextAnswer({
    question,
    updateAnswerMap,
    updateAnswerValidationMap,
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
      {question.questionType === 'CHECKBOX' && (
        <>
          {question.optionGroup?.options.map((option) => (
            <CheckboxItem
              key={option.optionId}
              id={option.optionId.toString()}
              isChecked={isSelectedCheckbox(option.optionId)}
              disabled={false}
              label={option.content}
              handleChange={handleCheckboxChange}
            />
          ))}
          <S.LimitGuideMessage>
            {isOpenLimitGuide && <p>😅 최대 {question.optionGroup?.maxCount}개까지 선택가능해요.</p>}
          </S.LimitGuideMessage>
        </>
      )}

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
