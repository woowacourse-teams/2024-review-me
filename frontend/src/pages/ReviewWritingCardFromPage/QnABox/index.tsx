import React, { useState } from 'react';

import CheckboxItem from '@/components/common/CheckboxItem';
import LongReviewItem from '@/components/common/LongReviewItem';
import { ReviewWritingAnswer, ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
  updatedAnswer: (answer: ReviewWritingAnswer) => void;
}
const TEXT_ANSWER_LENGTH = {
  min: 20,
  max: 1000,
};
const QnABox = ({ question, updatedAnswer }: QnABoxProps) => {
  const [selectedOptionList, setSelectedOptionList] = useState<number[]>([]);
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);
  const [textAnswer, setTextAnswer] = useState('');
  // 객관식
  const isSelectedCheckbox = (optionId: number) => {
    return selectedOptionList.includes(optionId);
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id } = event.currentTarget;
    // max를 넘어서는 선택하려 할 때
    if (isAboveSelectionLimit(Number(id))) {
      return setIsOpenLimitGuide(true);
    }
    // 유효한 객관식 문항 선택
    setIsOpenLimitGuide(false);

    const newSelectedOptionList = makeNewSelectedOptionList(event);
    setSelectedOptionList(newSelectedOptionList);
    updatedAnswer({ questionId: question.questionId, selectedOptionIds: newSelectedOptionList, text: null });
  };
  /**
   * checkbox의 change 이벤트에 따라 새로운 selectedOptionList를 반환하는 함수
   */
  const makeNewSelectedOptionList = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    const optionId = Number(id);

    if (checked) {
      return selectedOptionList.concat(optionId);
    }
    return selectedOptionList.filter((option) => option !== optionId);
  };

  const isMaxCheckedNumber = () => {
    if (!question.optionGroup) return;
    return selectedOptionList.length >= question.optionGroup.maxCount;
  };
  // NOTE: change 시 마다 상태 변경되어서, 디바운스를 적용할 지 고민...
  /**
   * 선택 가능한 문항 수를 넘어서 문항을 선택하려 하는지 여부
   */
  const isAboveSelectionLimit = (optionId: number) => !!(isMaxCheckedNumber() && !isSelectedCheckbox(optionId));
  // 서술형
  const handelTextAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = event.target;
    const { min, max } = TEXT_ANSWER_LENGTH;
    const isValidatedText = value.length >= min && value.length <= max;
    // TODO: XSS 방어 되는 지 확인해봐야함
    if (isValidatedText) {
      setTextAnswer(value);
      updatedAnswer({ questionId: question.questionId, selectedOptionIds: null, text: value });
    }
  };

  return (
    <section>
      <p>{question.content}</p>
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
      {question.questionType === 'TEXT' && (
        <LongReviewItem
          initialValue={textAnswer}
          minLength={TEXT_ANSWER_LENGTH.min}
          maxLength={TEXT_ANSWER_LENGTH.max}
          handleTextareaChange={handelTextAnswerChange}
        />
      )}
    </section>
  );
};

export default QnABox;
