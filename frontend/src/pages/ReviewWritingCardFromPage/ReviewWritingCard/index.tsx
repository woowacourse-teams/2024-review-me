import { useState } from 'react';

import { Button } from '@/components';
import { ButtonStyleType, ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

interface PrevButtonProps {
  cardIndex: number;
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const PrevButton = ({ cardIndex, handleCurrentCardIndex }: PrevButtonProps) => {
  const styledType: ButtonStyleType = cardIndex ? 'secondary' : 'disabled';

  return (
    <Button disabled={!!cardIndex} styleType={styledType} onClick={() => handleCurrentCardIndex('prev')}>
      이전
    </Button>
  );
};

interface NextButtonProps {
  disabled: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const NextButton = ({ disabled, handleCurrentCardIndex }: NextButtonProps) => {
  const styledType: ButtonStyleType = disabled ? 'disabled' : 'primary';

  return (
    <Button disabled={disabled} styleType={styledType} onClick={() => handleCurrentCardIndex('next')}>
      다음
    </Button>
  );
};

interface ReviewWritingCardProps {
  currentCardIndex: number;
  cardIndex: number;
  isLastCard: boolean;
  cardSection: ReviewWritingCardSection;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const ReviewWritingCard = ({
  currentCardIndex,
  cardIndex,
  isLastCard,
  cardSection,
  handleCurrentCardIndex,
}: ReviewWritingCardProps) => {
  const [answerMap, setAnswerMap] = useState<Map<number, ReviewWritingAnswer>>();

  const updatedAnswer = (answer: ReviewWritingAnswer) => {
    const newAnswerMap = new Map(answerMap);
    newAnswerMap.set(answer.questionId, answer);
    setAnswerMap(newAnswerMap);
  };

  const isValidateAnswerList = () => {
    if (!answerMap) return;

    return cardSection.questions.every((question) => {
      const { questionId, optionGroup } = question;
      const answer = answerMap.get(questionId);
      if (!answer) return false;
      // 객관식 인 경우
      if (optionGroup) {
        return !!answer.selectedOptionIds?.length;
      }
      // 서술형
      return !!answer.text?.length;
    });
  };

  return (
    <div>
      <p>{cardSection.header}</p>
      {cardSection.questions.map((question) => (
        <QnABox key={question.questionId} question={question} updatedAnswer={updatedAnswer} />
      ))}
      <div>
        <PrevButton
          cardIndex={cardIndex}
          currentCardIndex={currentCardIndex}
          handleCurrentCardIndex={handleCurrentCardIndex}
        />
        {isLastCard ? (
          <div>
            <Button styleType="primary" onClick={() => {}}>
              제출
            </Button>
            <Button styleType="secondary">미리보기</Button>
          </div>
        ) : (
          <NextButton disabled={!isValidateAnswerList()} handleCurrentCardIndex={handleCurrentCardIndex} />
        )}
      </div>
    </div>
  );
};

export default ReviewWritingCard;
