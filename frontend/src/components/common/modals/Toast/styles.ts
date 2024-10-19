import { css, keyframes } from '@emotion/react';
import styled from '@emotion/styled';

import media from '@/utils/media';

import { ToastPositionType } from '.';

interface ToastModalProps {
  duration: number;
  position: ToastPositionType;
}

// 위에서 아래로 내려오는 애니메이션
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

// 아래에서 다시 위로 올라가는 애니메이션
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

// 아래에서 위로 올라오는 애니메이션
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

// 위에서 아래로 내려가는 애니메이션
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

const getToastPositionStyles = (position: ToastPositionType, duration: number) => {
  return css`
    ${position === 'top' &&
    css`
      top: 2rem;
      animation:
        ${fadeInDown} 0.5s ease-out forwards,
        ${fadeOutUp} 0.5s ease-out forwards;
      animation-delay: 0s, ${duration - 0.5}s;
    `}

    ${position === 'bottom' &&
    css`
      bottom: 2rem;
      animation:
        ${fadeInUp} 0.5s ease-out forwards,
        ${fadeOutDown} 0.5s ease-out forwards;
      animation-delay: 0s, ${duration - 0.5}s;
    `}
  `;
};

export const ToastModalContainer = styled.div<ToastModalProps>`
  background-color: #626262;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.8rem;

  z-index: ${({ theme }) => theme.zIndex.modal};

  position: fixed;

  ${({ position, duration }) => getToastPositionStyles(position, duration)}

  left: 50%;
  transform: translateX(-50%);

  padding: 1rem 3rem;

  font-size: ${({ theme }) => theme.fontSize.small};

  border-radius: ${({ theme }) => theme.borderRadius.basic};
  box-shadow: 0.8rem 0.8rem 1.6rem rgba(0, 0, 0, 0.2);

  border: none;

  ${media.xxSmall} {
    padding: 1rem 2rem;
  }
`;

export const WarningIcon = styled.img`
  width: 2rem;
  height: 2rem;
`;

export const ErrorMessage = styled.span`
  white-space: nowrap;
`;
