import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

export const ProgressBar = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
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

  width: 12rem;
  height: 3rem;

  font-size: ${({ theme }) => theme.fontSize.small};
  font-weight: ${({ $isCurrentStep, theme }) => $isCurrentStep && theme.fontWeight.bold};
  text-overflow: ellipsis;
  white-space: nowrap;

  border: ${({ $isCurrentStep, theme }) => ($isCurrentStep ? `0.2rem solid ${theme.colors.primary}` : '')};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  ${({ $isDone, $isMovingAvailable, theme }) => getStepButtonStyle($isDone, $isMovingAvailable, theme)}
`;
