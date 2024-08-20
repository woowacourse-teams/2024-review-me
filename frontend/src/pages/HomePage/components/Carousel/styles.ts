import styled from '@emotion/styled';

export const CarouselContainer = styled.div`
  position: relative;
  width: 100%;
  height: 55vh;

  overflow: hidden;
  max-width: 80rem;
`;

export const SlideList = styled.div`
  display: flex;
  align-items: center;
  transition: transform 0.5s ease-in-out;
  width: 100%;
  height: 100%;
`;

export const SlideItem = styled.div`
  min-width: 100%;
  height: 100%;

  display: flex;
  align-items: center;
  justify-content: center;

  position: relative;
`;

export const SlideContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  width: 100%;

  position: absolute;
  left: 0;

  img {
    width: 80%;
    height: 100%;
  }
`;

export const Component = styled.div`
  position: absolute;
  right: 5rem;
  top: 10rem;
`;

export const PrevButton = styled.button`
  position: absolute;
  top: 50%;
  left: 1rem;
  transform: translateY(-50%);
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  z-index: 1;
`;

export const NextButton = styled.button`
  position: absolute;
  top: 50%;
  right: 1rem;
  transform: translateY(-50%);
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  z-index: 1;
`;
