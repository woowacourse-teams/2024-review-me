import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

import CardSliderController from './../CardSliderController/index';

interface ReviewWritingCardProps {
  currentCardIndex: number;
  isLastCard: boolean;
  cardSection: ReviewWritingCardSection;
  isAbleNextStep: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
  updatedAnswerMap: (answer: ReviewWritingAnswer) => void;
}

const ReviewWritingCard = ({
  currentCardIndex,
  isLastCard,
  cardSection,
  isAbleNextStep,
  handleCurrentCardIndex,
  updatedAnswerMap,
}: ReviewWritingCardProps) => {
  return (
    <div>
      <p>{cardSection.header}</p>
      {cardSection.questions.map((question) => (
        <QnABox key={question.questionId} question={question} updatedAnswerMap={updatedAnswerMap} />
      ))}
      <div>
        <CardSliderController.PrevButton
          currentCardIndex={currentCardIndex}
          handleCurrentCardIndex={handleCurrentCardIndex}
        />
        {isLastCard ? (
          <>
            <CardSliderController.PreviewButton handlePreviewClick={() => {}} />
            <CardSliderController.SubmitButton handleSubmitButtonClick={() => {}} />
          </>
        ) : (
          <CardSliderController.NextButton disabled={!isAbleNextStep} handleCurrentCardIndex={handleCurrentCardIndex} />
        )}
      </div>
    </div>
  );
};

export default ReviewWritingCard;
