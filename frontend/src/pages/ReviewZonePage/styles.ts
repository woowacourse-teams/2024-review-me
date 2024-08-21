import styled from '@emotion/styled';

export const ReviewZonePage = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const ReviewZoneMainImg = styled.img`
  width: 43rem;
  height: 23rem;
`;

export const ReviewGuideContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  padding-left: 0.2rem;
`;

export const ReviewGuide = styled.p`
  font-size: 2.2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  overflow-wrap: break-word;
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
  margin-top: 1.5rem;
`;

export const ButtonTextContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

export const ButtonText = styled.p`
  font-size: 2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const ButtonDescription = styled.p`
  font-size: 1.3rem;
`;
