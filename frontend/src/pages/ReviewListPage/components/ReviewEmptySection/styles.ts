import styled from '@emotion/styled';

export const Container = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  padding-top: 25rem;
`;

export const NullText = styled.span`
  position: absolute;
  top: -5rem;
  left: 22rem;
  transform: rotate(-20deg);

  font-size: 30rem;
  font-weight: ${({ theme }) => theme.fontWeight.bolder};
  color: #3f393b;
  text-shadow: 1rem 1rem 0.5rem rgba(0, 0, 0, 0.2);
`;

export const EmptyBox = styled.img`
  width: 20rem;
`;

export const EmptyReviewsText = styled.span`
  position: absolute;
  bottom: -4rem;
  left: 23rem;
  font-size: ${({ theme }) => theme.fontSize.medium};
`;
