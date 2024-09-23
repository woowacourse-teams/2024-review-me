import styled from '@emotion/styled';

import media from '@/utils/media';

import { ContentModalStyleProps } from '.';

export const ContentModalContainer = styled.div<ContentModalStyleProps>`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  overflow: hidden;
  display: flex;
  flex-direction: column;

  min-width: 30rem;
  max-width: ${({ theme }) => theme.contentModalSize.maxWidth};
  max-height: 90vh;
  padding: ${({ theme }) => theme.contentModalSize.padding};

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  ${media.xSmall} {
    padding: ${({ theme }) => theme.contentModalSize.smallPadding};
  }

  ${({ $style }) => $style && { ...$style }}
`;

export const ContentModalHeader = styled.div`
  display: flex;
  gap: 8rem;

  ${media.small} {
    gap: 5rem;
  }

  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 3rem;
  margin-bottom: 1.5rem;
`;

export const Title = styled.span`
  font-size: 1.8rem;

  ${media.small} {
    font-size: 1.6rem;
  }

  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;

export const Contents = styled.div`
  overflow-y: auto;
  width: 100%;
`;

export const CloseButton = styled.button`
  width: 2.4rem;
  height: 2.4rem;
`;
