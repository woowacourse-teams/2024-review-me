import styled from '@emotion/styled';

export const ReviewZonePage = styled.div`
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

  width: 28rem;

  padding-left: 0.2rem;
`;

export const ReviewGuide = styled.p`
  font-size: 2.2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};
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
  font-size: 1.1rem;
`;
