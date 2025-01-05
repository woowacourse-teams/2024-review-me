import styled from '@emotion/styled';

import media from '@/utils/media';

interface DropdownStyleProps {
  $isOpened: boolean;
}

export const ProfileSection = styled.section`
  cursor: pointer;
  position: relative;
  width: fit-content;
`;

export const ProfileContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
  padding: 0 1rem;
`;

export const ProfileImageWrapper = styled.div`
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 2.8rem;
  height: 2.8rem;

  background-color: ${({ theme }) => theme.colors.gray};
  border-radius: 50%;

  ${media.small} {
    width: 2.6rem;
    height: 2.6rem;
  }
`;

export const ProfileId = styled.p`
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  ${media.small} {
    display: none;
  }
`;

export const ArrowIcon = styled.img<DropdownStyleProps>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  width: 2rem;
  height: 2rem;
  transition: transform 0.3s ease-in-out;

  ${media.small} {
    display: none;
  }
`;
