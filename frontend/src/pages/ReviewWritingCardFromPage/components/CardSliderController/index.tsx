import { Button } from '@/components';
import { ButtonStyleType } from '@/types';

interface PrevButtonProps {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const PrevButton = ({ currentCardIndex, handleCurrentCardIndex }: PrevButtonProps) => {
  const styledType: ButtonStyleType = currentCardIndex ? 'secondary' : 'disabled';

  return (
    <Button
      disabled={!!currentCardIndex}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('prev')}
    >
      이전
    </Button>
  );
};

interface NextButtonProps {
  isAbleNextStep: boolean;
  handleCurrentCardIndex: (direction: 'prev' | 'next') => void;
}

const NextButton = ({ isAbleNextStep, handleCurrentCardIndex }: NextButtonProps) => {
  const styledType: ButtonStyleType = isAbleNextStep ? 'primary' : 'disabled';

  return (
    <Button
      disabled={!isAbleNextStep}
      styleType={styledType}
      type={'button'}
      onClick={() => handleCurrentCardIndex('next')}
    >
      다음
    </Button>
  );
};

interface SubmitButtonProps {
  isAbleNextStep: boolean;
  handleSubmitButtonClick: () => void;
}
const SubmitButton = ({ isAbleNextStep, handleSubmitButtonClick }: SubmitButtonProps) => {
  const styledType: ButtonStyleType = isAbleNextStep ? 'primary' : 'disabled';
  return (
    <Button disabled={!isAbleNextStep} styleType={styledType} type={'submit'} onClick={handleSubmitButtonClick}>
      제출
    </Button>
  );
};
interface RecheckButton {
  handleRecheckButtonClick: () => void;
}

const RecheckButton = ({ handleRecheckButtonClick }: RecheckButton) => {
  return (
    <Button styleType="secondary" type={'button'} onClick={handleRecheckButtonClick}>
      제출 전 확인
    </Button>
  );
};

export interface CardSliderControllerProps extends PrevButtonProps, NextButtonProps, SubmitButtonProps, RecheckButton {}

const CardSliderController = () => {
  return <></>;
};

CardSliderController.PrevButton = PrevButton;
CardSliderController.NextButton = NextButton;
CardSliderController.SubmitButton = SubmitButton;
CardSliderController.RecheckButton = RecheckButton;

export default CardSliderController;
