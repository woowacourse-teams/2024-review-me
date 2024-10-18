import styled from '@emotion/styled';

export const TooltipButton = styled.button`
  position: relative;
`;
export const HelperIcon = styled.img`
  width: 1.6rem;
  height: 1.6rem;
`;

export const Message = styled.aside`
  position: absolute;
  /* HelperIcon과 말풍선 삼각형 높이 */
  top: calc(1.6rem * 2);
  right: -4rem;

  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: max-content;
  padding: 1.6rem 1.4rem;

  font-size: ${({ theme }) => theme.fontSize.small};

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  -webkit-box-shadow: 0 0 1rem 0.5rem rgba(236, 236, 236, 0.63);
  box-shadow: 0 0 1rem 0.5rem rgba(236, 236, 236, 0.63);

  &:before {
    content: '';

    position: absolute;
    top: 0rem;
    right: 3rem;
    transform: translate(0%, -50%);

    border-right: 1.6rem solid transparent;
    border-bottom: 1.6rem solid ${({ theme }) => theme.colors.lightPurple};
    border-left: 1.6rem solid transparent;
  }

  @media screen and (max-width: 500px) {
    /* 2rem: 상위 부모 요소의 padding 값 */
    max-width: calc(90vw - 2rem * 3);
    font-size: 1.2rem;
  }
`;
