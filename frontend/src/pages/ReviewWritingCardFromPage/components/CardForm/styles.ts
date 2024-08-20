import styled from '@emotion/styled';

export const CardForm = styled.form`
  position: relative;
  overflow: hidden;
  width: ${({ theme }) => theme.formWidth};
  overflow-wrap: break-word;
`;

interface SlideContainerProps {
  $translateX: number;
  $height: string;
}
export const SliderContainer = styled.div<SlideContainerProps>`
  transform: ${({ $translateX }) => `translateX(-${$translateX}px)`};

  display: flex;

  width: 100%;
  height: ${({ $height }) => $height};
  margin-bottom: 2rem;

  transition: transform 0.5s ease-in-out;
`;

export const Slide = styled.div`
  flex: 0 0 100%;

  box-sizing: border-box;
  height: fit-content;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const RevieweeDescription = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 2rem;
`;
export const ProjectInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  width: calc(100% - 6rem);
  margin-left: 1rem;
`;

export const ProjectName = styled.p`
  margin-top: 0;
  font-size: ${({ theme }) => theme.fontSize.medium};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const RevieweeName = styled.span`
  color: ${({ theme }) => theme.colors.primary};
`;

export const SubmitErrorMessage = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: start;

  p {
    width: max-content;
    margin: 0;
  }
`;
