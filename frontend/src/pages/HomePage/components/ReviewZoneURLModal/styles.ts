import styled from '@emotion/styled';

import media from '@/utils/media';

export const ReviewZoneURLModal = styled.div`
  display: flex;
  flex-direction: column;
  width: 52rem;
  height: 18rem;

  ${media.small`
    button {
      display: none;
    }
  `}
`;

export const RequestURLContainer = styled.div`
  display: flex;
  align-items: center;

  max-width: 100%;

  button {
    display: none;
  }

  ${media.small`
    button {
      display: block;
    }
  `}
`;

export const ModalTitle = styled.p`
  margin-bottom: 4.5rem;
  font-size: 2rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  ${media.small`
    font-size: 1.8rem;
    margin-bottom: 2.2rem;
  `}
`;

export const ReviewZoneURLModalItem = styled.div`
  display: flex;
  gap: 1.8rem;
  align-items: center;
  justify-content: space-between;

  font-size: 1.5rem;

  ${media.small`
    flex-direction: column;
    align-items: flex-start;
  `}
`;

export const DataName = styled.span`
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  white-space: nowrap;
  margin-right: 1rem;

  ${media.xSmall`
    font-size: 1.6rem;
  `}
`;

export const Data = styled.span`
  flex: 2;
  color: ${({ theme }) => theme.colors.gray};
`;

export const CheckContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;

  margin-top: 2.5rem;

  font-size: 1.5rem;
`;

export const Checkbox = styled.input`
  cursor: pointer;
  width: 1rem;
  height: 1rem;
  margin-right: 0.3rem;
`;

export const CheckMessage = styled.p`
  font-size: 1.3rem;
`;

export const WarningMessage = styled.p`
  margin-top: 1rem;
  font-size: smaller;
  color: ${({ theme }) => theme.colors.red};

  ${media.small`
    margin-top: 0;
    font-size: 1rem;
  `}
`;
