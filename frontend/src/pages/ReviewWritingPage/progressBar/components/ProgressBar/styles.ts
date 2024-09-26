import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const ProgressBarContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin-bottom: 1.7rem;
`;

export const ProgressBar = styled.div`
  display: flex;
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
  overflow: hidden;

  width: 100%;
  min-width: 12rem;
  max-width: 15rem;
  height: 3rem;
  padding: 0 1rem;

  font-size: 1.3rem;
  font-weight: ${({ $isCurrentStep, theme }) => $isCurrentStep && theme.fontWeight.bold};
  text-overflow: ellipsis;
  white-space: nowrap;

  border: ${({ $isCurrentStep, theme }) => ($isCurrentStep ? `0.2rem solid ${theme.colors.primary}` : '')};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  ${({ $isDone, $isMovingAvailable, theme }) => getStepButtonStyle($isDone, $isMovingAvailable, theme)}
`;
