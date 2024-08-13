import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

import CardSliderController, { CardSliderControllerProps } from './../CardSliderController/index';
import * as S from './style';

interface ReviewWritingCardProps extends CardSliderControllerProps {
  cardIndex: number;
  isLastCard: boolean;
  cardSection: ReviewWritingCardSection;
  updateAnswerMap: (answer: ReviewWritingAnswer) => void;
  updateAnswerValidationMap: (answer: ReviewWritingAnswer, isValidatedAnswer: boolean) => void;
}

const ReviewWritingCard = ({
  cardIndex,
  currentCardIndex,
  isLastCard,
  cardSection,
  isAbleNextStep,
  handleCurrentCardIndex,
  updateAnswerMap,
  updateAnswerValidationMap,
  handleRecheckButtonClick,
  handleSubmitButtonClick,
}: ReviewWritingCardProps) => {
  return (
    <S.ReviewWritingCard>
      <S.Header>{cardSection.header}</S.Header>
      <S.Main>
        {cardSection.questions.map((question) => (
          <QnABox
            key={question.questionId}
            question={question}
            updateAnswerMap={updateAnswerMap}
            updateAnswerValidationMap={updateAnswerValidationMap}
          />
        ))}

        <S.ButtonContainer>
          {!!currentCardIndex && !!cardIndex && (
            <CardSliderController.PrevButton
              currentCardIndex={currentCardIndex}
              handleCurrentCardIndex={handleCurrentCardIndex}
            />
          )}
          {isLastCard ? (
            <>
              <CardSliderController.RecheckButton handleRecheckButtonClick={handleRecheckButtonClick} />
              <CardSliderController.SubmitButton
                isAbleNextStep={isAbleNextStep}
                handleSubmitButtonClick={handleSubmitButtonClick}
              />
            </>
          ) : (
            <CardSliderController.NextButton
              isAbleNextStep={isAbleNextStep}
              handleCurrentCardIndex={handleCurrentCardIndex}
            />
          )}
        </S.ButtonContainer>
      </S.Main>
    </S.ReviewWritingCard>
  );
};

export default ReviewWritingCard;
