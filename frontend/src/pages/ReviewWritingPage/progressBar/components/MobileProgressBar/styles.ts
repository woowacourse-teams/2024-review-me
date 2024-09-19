import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const ProgressBarContainer = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 5rem;
  margin-bottom: 1.7rem;
`;

export const ProgressBar = styled.div`
  scroll-snap-type: x mandatory;

  overflow-x: scroll;
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: flex-start;

  width: 100%;
  height: 5rem;
  padding: 0 1rem;

  &::-webkit-scrollbar {
    display: none;
  }

  & > img {
    width: 3rem;
    height: 3rem;
  }
`;

export const StepWrapper = styled.div<{ $isCurrentStep: boolean }>`
  scroll-snap-align: center;

  display: flex;
  flex: 0 0 auto;
  align-items: center;
  justify-content: space-between;

  width: 50%;
  margin: 0 ${({ $isCurrentStep }) => ($isCurrentStep ? '1.5rem' : 0)};
`;

interface StepButtonStyleProps {
  $isDone: boolean;
  $isMovingAvailable: boolean;
  $isCurrentStep: boolean;
}

const defaultStyle = (theme: Theme) => css`
  cursor: pointer;
  background-color: ${theme.colors.white};
`;

const disabledStyle = (theme: Theme) => css`
  pointer-events: none;
  color: ${theme.colors.disabledText};
  background-color: ${theme.colors.disabled};
  border-color: ${theme.colors.disabled};
`;

const completedStyle = (theme: Theme) => css`
  cursor: pointer;
  background-color: ${theme.colors.palePurple};
`;

const getStepButtonStyle = ($isDone: boolean, $isMovingAvailable: boolean, theme: Theme) => {
  if (!$isMovingAvailable) return disabledStyle(theme);
  if ($isDone) return completedStyle(theme);
  return defaultStyle(theme);
};

export const StepButton = styled.button<StepButtonStyleProps>`
  transform: scale(${({ $isCurrentStep }) => ($isCurrentStep ? 1.1 : 1)});

  width: 100%;
  min-width: 12rem;
  max-width: 15rem;
  height: 3rem;
  padding: 0 1rem;

  font-size: 1.3rem;

  border: ${({ $isCurrentStep, theme }) => ($isCurrentStep ? `0.2rem solid ${theme.colors.primary}` : '')};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${({ $isDone, $isMovingAvailable, theme }) => getStepButtonStyle($isDone, $isMovingAvailable, theme)}
`;

export const EmptyBlock = styled.div`
  width: 0.1rem;
  height: 0.1rem;
  visibility: hidden;
`;

export const EmptyStepWrapper = styled.div`
  display: flex;
  min-width: 50%;
  height: 3rem;
  visibility: hidden;
`;
