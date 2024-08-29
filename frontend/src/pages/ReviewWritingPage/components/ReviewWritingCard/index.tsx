import { useCheckNextStepAvailability } from '@/hooks';
import { ReviewWritingCardSection } from '@/types';

import QnABox from '../QnABox';

import CardSliderController, { CardSliderControllerProps } from './../CardSliderController/index';
import * as S from './style';

interface ReviewWritingCardProps extends Omit<CardSliderControllerProps, 'isAbleNextStep'> {
  cardIndex: number;
  isLastCard: boolean;
  cardSection: ReviewWritingCardSection;
  handleNextClick: () => void;
}

const ReviewWritingCard = ({
  cardIndex,
  currentCardIndex,
  isLastCard,
  cardSection,
  handleNextClick,
  handleCurrentCardIndex,
  handleRecheckButtonClick,
  handleSubmitConfirmModalOpenButtonClick,
}: ReviewWritingCardProps) => {
  const { isAbleNextStep } = useCheckNextStepAvailability({ currentCardIndex });

  return (
    <S.ReviewWritingCard>
      <S.Header>{cardSection.header}</S.Header>
      <S.Main>
        {cardSection.questions.map((question) => (
          <QnABox key={question.questionId} question={question} />
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
              <CardSliderController.RecheckButton
                isAbleNextStep={isAbleNextStep}
                handleRecheckButtonClick={handleRecheckButtonClick}
              />
              <CardSliderController.ConfirmModalOpenButton
                isAbleNextStep={isAbleNextStep}
                handleSubmitConfirmModalOpenButtonClick={handleSubmitConfirmModalOpenButtonClick}
              />
            </>
          ) : (
            <CardSliderController.NextButton isAbleNextStep={isAbleNextStep} handleCurrentCardIndex={handleNextClick} />
          )}
        </S.ButtonContainer>
      </S.Main>
    </S.ReviewWritingCard>
  );
};

export default ReviewWritingCard;
