import styled from '@emotion/styled';

import media from '@/utils/media';

export const InfinityCarouselContainer = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 1.6rem;

  width: 100%;
  max-width: 80rem;

  ${media.small} {
    width: 80%;
    height: 70%;
  }
`;

export const SlideList = styled.div`
  will-change: transform;

  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;
`;

export const SlideItem = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 100%;
`;

export const SlideContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 100%;

  img {
    width: 80%;
  }
`;

export const PrevButton = styled.button`
  cursor: pointer;

  position: absolute;
  z-index: 1;
  top: 50%;
  left: 1rem;
  transform: translateY(-50%);

  font-size: 2rem;

  background: none;
  border: none;

  ${media.xSmall} {
    display: none;
  }
`;

export const NextButton = styled.button`
  cursor: pointer;

  position: absolute;
  z-index: 1;
  top: 50%;
  right: 1rem;
  transform: translateY(-50%);

  font-size: 2rem;

  background: none;
  border: none;

  ${media.xSmall} {
    display: none;
  }
`;

export const IndicatorWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

export const Indicator = styled.div<{ focused: boolean }>`
  cursor: pointer;

  width: 1rem;
  height: 1rem;
  margin: 0 0.5rem;

  background-color: ${({ focused, theme }) => (focused ? theme.colors.black : theme.colors.placeholder)};
  border-radius: 50%;

  transition: background-color 0.3s ease;

  ${media.xSmall} {
    width: 0.8rem;
    height: 0.8rem;
  }

  ${media.xxSmall} {
    width: 0.6rem;
    height: 0.6rem;
  }
`;
