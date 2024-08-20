import styled from '@emotion/styled';

export const CarouselContainer = styled.div`
  position: relative;

  overflow: hidden;

  width: 100%;
  max-width: 80rem;
  height: 55vh;
`;

export const SlideList = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;

  transition: transform 0.5s ease-in-out;
`;

export const SlideItem = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 100%;
  height: 100%;
`;

export const SlideContent = styled.div`
  position: absolute;
  left: 0;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 100%;

  img {
    width: 80%;
    height: 100%;
  }
`;

export const Component = styled.div`
  position: absolute;
  top: 10rem;
  right: 5rem;
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
`;
