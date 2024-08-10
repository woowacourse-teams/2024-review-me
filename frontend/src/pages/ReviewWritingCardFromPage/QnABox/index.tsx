import React, { useState } from 'react';

import CheckboxItem from '@/components/common/CheckboxItem';
import LongReviewItem from '@/components/common/LongReviewItem';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
}

const QnABox = ({ question }: QnABoxProps) => {
  const [selectedOptionList, setSelectedOptionList] = useState<string[]>([]);
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);
  const [textAnswer, setTextAnswer] = useState('');
  // 객관식
  const isSelectedCheckbox = (optionId: string) => {
    return selectedOptionList.includes(optionId);
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id } = event.currentTarget;
    if (isAboveSelectionLimit(id)) {
      return setIsOpenLimitGuide(true);
    }
    setIsOpenLimitGuide(false);
    changeChecked(event);
  };
  /**
   * checkbox의 change 이벤트에 따라 selectedOptionList의 상태를 변경하는 함수
   */
  const changeChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { id, checked } = event.currentTarget;
    if (checked) {
      setSelectedOptionList((prev) => prev.concat(id));
      return;
    }
    setSelectedOptionList((prev) => prev.filter((optionId) => optionId !== id));
  };

  const isMaxCheckedNumber = () => {
    if (!question.optionGroup) return;
    return selectedOptionList.length >= question.optionGroup.maxCount;
  };
  /**
   * 선택 가능한 문항 수를 넘어서 문항을 선택하려 하는지 여부
   */
  const isAboveSelectionLimit = (optionId: string) => {
    return !!(isMaxCheckedNumber() && !isSelectedCheckbox(optionId));
  };
  // 서술형
  const handelTextAnswerChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {};

  return (
    <section>
      <p>{question.content}</p>
      {question.questionType === 'CHECKBOX' &&
        question.optionGroup?.options.map((option) => (
          <CheckboxItem
            key={option.optionId}
            id={option.optionId.toString()}
            isChecked={isSelectedCheckbox(option.optionId.toString())}
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
          minLength={20}
          maxLength={1000}
          handleTextareaChange={handelTextAnswerChange}
        />
      )}
    </section>
  );
};

export default QnABox;
