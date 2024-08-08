import styled from '@emotion/styled';

export const CardLayout = styled.div`
  position: relative;

  overflow: hidden;

  width: ${({ theme }) => theme.formWidth};

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const SliderContainer = styled.div<{ $translateX: number }>`
  transform: ${({ $translateX }) => `translateX(-${$translateX}px)`};
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
  gap: 2rem;
  justify-content: flex-end;
  padding: 2.5rem;

  button {
    width: 8rem;
    height: 3.5rem;
  }
`;

export const LimitGuideMessage = styled.div`
  position: absolute;
  width: fit-content;
  padding: 1rem 2rem;
  background-color: ${({ theme }) => theme.colors.lightPurple};
`;
