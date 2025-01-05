import styled from '@emotion/styled';

import media from '@/utils/media';

interface ProfileTabStyleProps {
  $isDisplayedOnlyMobile: boolean;
}

export const ProfileTabContainer = styled.section`
  position: absolute;
  z-index: ${({ theme }) => theme.zIndex.profileTab};
  top: 5rem;
  right: 0;

  display: flex;
  flex-direction: column;

  width: max-content;
  min-width: 100%;
  height: fit-content;
  padding: 1rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: 0.8rem;
  box-shadow:
    0 0.5rem 0.5rem -0.3rem rgba(0, 0, 0, 0.2),
    0 0.8rem 1rem 0.1rem rgba(0, 0, 0, 0.14),
    0 0.3rem 1.4rem 0.2rem rgba(0, 0, 0, 0.12);
`;

export const ReadonlyItemWrapper = styled.div<ProfileTabStyleProps>`
  cursor: default;

  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'flex')};
  align-items: center;

  height: 3rem;
  padding: 1rem;

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'flex'};
  }
`;

export const ActionItemWrapper = styled.div<ProfileTabStyleProps>`
  cursor: pointer;

  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'flex')};
  align-items: center;

  height: 3rem;
  padding: 1rem;

  border-radius: 0.8rem;

  :hover {
    background-color: ${({ theme }) => theme.colors.lightGray};
  }

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'flex'};
  }
`;

export const Divider = styled.hr<ProfileTabStyleProps>`
  display: ${({ $isDisplayedOnlyMobile }) => ($isDisplayedOnlyMobile ? 'none' : 'block')};

  width: 100%;
  height: 0;
  margin: 0.5rem 0;
  padding: 0;

  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};

  ${media.small} {
    display: ${({ $isDisplayedOnlyMobile }) => $isDisplayedOnlyMobile && 'block'};
  }
`;
