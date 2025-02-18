import styled from '@emotion/styled';

import media from '@/utils/media';

export const DetailedReview = styled.div<{ $layoutStyle?: React.CSSProperties }>`
  width: 70%;
  margin-top: 2rem;
  padding: 2rem 3rem;

  border: 0.2rem solid ${({ theme }) => theme.colors.disabled};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.small} {
    padding: 2rem 1.8rem;
  }

  ${({ $layoutStyle }) => $layoutStyle && { ...$layoutStyle }};
`;

export const Separator = styled.div`
  width: 100%;
  height: 0.3rem;
  margin: 3rem 0;
  background-color: ${({ theme }) => theme.colors.disabled};
`;

export const DetailedReviewContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4rem;
`;

export const ReviewContentContainer = styled.div`
  ${media.xSmall} {
    padding: 0 1rem;
  }
`;
