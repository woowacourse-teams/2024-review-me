import styled from '@emotion/styled';

export const CardLayout = styled.div`
  position: relative;
  overflow: hidden;
  width: ${({ theme }) => theme.formWidth};

  border-radius: ${({ theme }) => theme.borderRadius.basic};
  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
`;

export const SliderContainer = styled.div<{ translateX: number }>`
  transform: ${({ translateX }) => `translateX(-${translateX}px)`};
  display: flex;
  width: 100%;
  transition: transform 0.5s ease-in-out;
`;

export const Slide = styled.div`
  flex: 0 0 100%;
  box-sizing: border-box;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 2rem;

  button {
    width: 10rem;
  }
`;
