import { useEffect, useRef } from 'react';

import NavigateNextIcon from '@/assets/navigateNext.svg';
import useStepList from '@/pages/ReviewWritingPage/progressBar/hooks/useStepList';
import { Direction } from '@/pages/ReviewWritingPage/types';

import * as S from './styles';

interface MobileProgressBarProps {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: Direction) => void;
}

const MobileProgressBar = ({ currentCardIndex, handleCurrentCardIndex }: MobileProgressBarProps) => {
  const { stepList } = useStepList({ currentCardIndex });

  const stepRefs = useRef<HTMLDivElement[]>([]);

  useEffect(() => {
    if (stepRefs.current[currentCardIndex]) {
      stepRefs.current[currentCardIndex].scrollIntoView({
        behavior: 'smooth',
        inline: 'center',
      });
    }
  }, [currentCardIndex]);

  const handleClick = (index: number) => {
    const { isMovingAvailable } = stepList[index];
    if (isMovingAvailable) handleCurrentCardIndex(index);
  };

  return (
    <S.ProgressBarContainer>
      <S.ProgressBar>
        <S.EmptyStepWrapper />
        {stepList.map((step, index) => (
          <>
            <S.StepWrapper
              key={step.sectionId}
              $isCurrentStep={index === currentCardIndex}
              ref={(element) => (stepRefs.current[index] = element!)}
            >
              <S.EmptyBlock />
              <S.StepButton
                key={step.sectionId}
                $isDone={step.isDone}
                $isMovingAvailable={step.isMovingAvailable}
                $isCurrentStep={step.isCurrentStep}
                onClick={() => handleClick(index)}
                type="button"
              >
                {step.sectionName}
              </S.StepButton>
              <S.EmptyBlock />
            </S.StepWrapper>
            {index < stepList.length - 1 && <img src={NavigateNextIcon} alt="Next" />}
          </>
        ))}
        <S.EmptyStepWrapper />
      </S.ProgressBar>
    </S.ProgressBarContainer>
  );
};

export default MobileProgressBar;
