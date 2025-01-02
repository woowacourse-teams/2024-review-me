import styled from '@emotion/styled';

import media from '@/utils/media';

export const DetailedReviewPageContents = styled.div`
  width: 70%;
  margin-top: 2rem;
  border: 0.2rem solid ${({ theme }) => theme.colors.lightGray};

  border-radius: ${({ theme }) => theme.borderRadius.basic};

  padding: 2rem 3rem;

  ${media.medium} {
    width: 80%;
  }

  ${media.small} {
    width: 92%;
  }
`;

export const Separator = styled.div`
  width: 100%;
  height: 0.3rem;
  background-color: ${({ theme }) => theme.colors.disabled};
  margin: 3rem 0;
`;

export const DetailedReviewContainer = styled.div`
  display: flex;
  flex-direction: column;

  gap: 4rem;
`;

export const ReviewContentContainer = styled.div`
  ${media.xSmall} {
    padding: 0 2rem;
  }
`;
