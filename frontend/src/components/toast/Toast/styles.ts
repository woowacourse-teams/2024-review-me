import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import media from '@/utils/media';

import { ToastPositionType } from '.';

interface ToastModalProps {
  $animationDurationMS: number;
  $position: ToastPositionType;
}

// position: top - 위에서 아래로 내려오는 애니메이션
const fadeInDown = keyframes`
  0% {
    opacity: 0;
    transform: translate(-50%, 0);
  }
  100% {
    opacity: 1;
    transform: translate(-50%, 100%);
  }
`;

// position: top - 아래에서 다시 위로 올라가는 애니메이션
const fadeOutUp = keyframes`
  0% {
    opacity: 1;
    transform: translate(-50%, 100%);
  }
  100% {
    opacity: 0;
    transform: translate(-50%, 0);
  }
`;

// position: bottom - 아래에서 위로 올라오는 애니메이션
const fadeInUp = keyframes`
  0% {
    opacity: 0;
    transform: translate(-50%, 100%);
  }
  100% {
    opacity: 1;
    transform: translate(-50%, 0);
  }
`;

// position: bottom - 위에서 아래로 내려가는 애니메이션
const fadeOutDown = keyframes`
  0% {
    opacity: 1;
    transform: translate(-50%, 0);
  }
  100% {
    opacity: 0;
    transform: translate(-50%, 100%);
  }
`;

const getToastPositionStyles = ($position: ToastPositionType, $animationDurationMS: number) => {
  return css`
    ${$position === 'top' &&
    css`
      top: 5%;
      animation:
        ${fadeInDown} 0.5s ease-out forwards,
        ${fadeOutUp} 0.5s ease-out forwards;
      animation-delay: 0s, ${$animationDurationMS - 0.5}s;
    `}

    ${$position === 'bottom' &&
    css`
      bottom: 5%;
      animation:
        ${fadeInUp} 0.5s ease-out forwards,
        ${fadeOutDown} 0.5s ease-out forwards;
      animation-delay: 0s, ${$animationDurationMS - 0.5}s;
    `}
  `;
};

export const ToastContainer = styled.div<ToastModalProps>`
  background-color: #626262;

  color: white;

  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.8rem;

  position: fixed;

  ${({ $position, $animationDurationMS }) => getToastPositionStyles($position, $animationDurationMS)}
  left: 50%;
  transform: translateX(-50%);

  padding: 1rem 3rem;

  font-size: ${({ theme }) => theme.fontSize.small};

  border-radius: ${({ theme }) => theme.borderRadius.basic};
  box-shadow: 0.4rem 0.4rem 0.8rem rgba(0, 0, 0, 0.2);
  border: none;

  ${media.xxSmall} {
    padding: 1rem 2rem;
  }
`;

export const ToastIcon = styled.img`
  width: 2rem;
  height: 2rem;
`;

export const ToastMessage = styled.span`
  white-space: nowrap;
`;
