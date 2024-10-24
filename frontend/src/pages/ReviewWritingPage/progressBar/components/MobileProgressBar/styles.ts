import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const ProgressBarContainer = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin-bottom: 1.7rem;
`;

export const ProgressBar = styled.div`
  scroll-snap-type: x mandatory;

  overflow-x: scroll;
  display: flex;

  width: 100%;
  padding: 1rem 0;

  &::-webkit-scrollbar {
    display: none;
  }

  & > img {
    width: 3rem;
    height: 3rem;
  }
`;

export const StepWrapper = styled.div`
  scroll-snap-align: center;

  display: flex;
  flex: 0 0 auto;
  align-items: center;
  justify-content: space-between;

  width: 100%;
`;

interface StepButtonStyleProps {
  $isDone: boolean;
  $isMovingAvailable: boolean;
  $isCurrentStep: boolean;
}

const defaultStyle = (theme: Theme) => css`
  cursor: pointer;
  background-color: ${theme.colors.white};
  border: 0.2rem solid ${theme.colors.palePurple};
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
  min-width: 15rem;
  max-width: 20rem;
  height: 3rem;
  margin: 0 ${({ $isCurrentStep }) => ($isCurrentStep ? '2.5rem' : '2rem')};
  padding: 0 1rem;

  font-size: 1.3rem;

  ${({ $isDone, $isMovingAvailable, theme }) => getStepButtonStyle($isDone, $isMovingAvailable, theme)}
  border: ${({ $isCurrentStep, theme }) => ($isCurrentStep ? `0.2rem solid ${theme.colors.primary}` : '')};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const DottedLine = styled.div`
  width: 100%;
  height: 0.1rem;
  margin: 0 0.2rem;
  border-top: 0.5rem dotted ${({ theme }) => theme.colors.lightGray};
`;

export const EmptyBlock = styled.div`
  width: 100%;
  height: 1rem;
  visibility: hidden;
`;
