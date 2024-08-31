import { useRecoilValue } from 'recoil';

import { Carousel } from '@/components';
import { CARD_FORM_MODAL_KEY } from '@/pages/ReviewWritingPage/constants';
import { ReviewWritingCard } from '@/pages/ReviewWritingPage/form/components';
import { CardSliderController } from '@/pages/ReviewWritingPage/slider/components';
import { useMovingStepAvailability, useSlideWidthAndHeight } from '@/pages/ReviewWritingPage/slider/hooks';
import { Direction } from '@/pages/ReviewWritingPage/types';
import { cardSectionListSelector } from '@/recoil';

import * as S from './style';

interface CardSliderProps {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: Direction) => void;
  handleOpenModal: (key: keyof typeof CARD_FORM_MODAL_KEY) => void;
}

const CardSlider = ({ currentCardIndex, handleCurrentCardIndex, handleOpenModal }: CardSliderProps) => {
  const cardSectionList = useRecoilValue(cardSectionListSelector);
  const { wrapperRef, slideHeight, slideWidth, makeId } = useSlideWidthAndHeight({ currentCardIndex });

  const { isAblePrevStep, isAbleNextStep, isLastCard } = useMovingStepAvailability({ currentCardIndex });

  const handleNextClick = () => {
    const nextIndex = currentCardIndex + 1;
    if (nextIndex < cardSectionList.length) {
      handleCurrentCardIndex('next');
    }
  };

  const handleRecheckButtonClick = () => {
    handleOpenModal('recheck');
  };

  const handleSubmitConfirmModalOpenButtonClick = () => {
    handleOpenModal('submitConfirm');
  };

  return (
    <Carousel ref={wrapperRef} translateX={currentCardIndex * slideWidth} height={slideHeight}>
      {cardSectionList?.map((section, index) => (
        <S.Slide id={makeId(index)} key={section.sectionId}>
          <ReviewWritingCard cardSection={section} />
          <S.ButtonContainer>
            {isAblePrevStep(index) && (
              <CardSliderController.PrevButton
                currentCardIndex={currentCardIndex}
                handleCurrentCardIndex={handleCurrentCardIndex}
              />
            )}
            {isLastCard() ? (
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
              <CardSliderController.NextButton
                isAbleNextStep={isAbleNextStep}
                handleCurrentCardIndex={handleNextClick}
              />
            )}
          </S.ButtonContainer>
        </S.Slide>
      ))}
    </Carousel>
  );
};

export default CardSlider;
