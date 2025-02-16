import styled from '@emotion/styled';

// large 초반 사이즈에서 레이아웃이 잘리는 것을 막기 위해 전용 미디어 쿼리 추가
export const LargeContentContainer = styled.div`
  display: flex;
  gap: 6rem;
  justify-content: center;

  @media (min-width: 1025px) and (max-width: 1100px) {
    gap: 1.4rem;
  }
`;
