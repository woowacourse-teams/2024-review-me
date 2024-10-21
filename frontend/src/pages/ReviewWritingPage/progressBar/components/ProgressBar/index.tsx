import React from 'react';

import NavigateNextIcon from '@/assets/navigateNext.svg';
import useStepList from '@/pages/ReviewWritingPage/progressBar/hooks/useStepList';
import { Direction } from '@/pages/ReviewWritingPage/types';

import * as S from './styles';

interface ProgressBarProps {
  currentCardIndex: number;
  handleCurrentCardIndex: (direction: Direction) => void;
}

const ProgressBar = ({ currentCardIndex, handleCurrentCardIndex }: ProgressBarProps) => {
  const { stepList } = useStepList({ currentCardIndex });

  const handleClick = (index: number) => {
    const { isMovingAvailable } = stepList[index];
    if (isMovingAvailable) handleCurrentCardIndex(index);
  };

  return (
    <S.ProgressBarContainer>
      <S.ProgressBar role="status" aria-live="polite">
        {stepList.map((step, index) => {
          return (
            <React.Fragment key={step.sectionId}>
              <S.StepButton
                $isDone={step.isDone}
                $isMovingAvailable={step.isMovingAvailable}
                $isCurrentStep={step.isCurrentStep}
                onClick={() => handleClick(index)}
                type="button"
                aria-label={
                  step.isCurrentStep
                    ? `현재 섹션은. 전체 ${stepList.length}개 섹션 중. ${index + 1}번째 섹션입니다. ${step.sectionName}`
                    : `${step.sectionName}`
                }
              >
                {step.sectionName}
              </S.StepButton>
              {index < stepList.length - 1 && <img src={NavigateNextIcon} alt="다음 화살표" />}
            </React.Fragment>
          );
        })}
      </S.ProgressBar>
    </S.ProgressBarContainer>
  );
};

export default ProgressBar;
