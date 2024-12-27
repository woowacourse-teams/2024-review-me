import styled from '@emotion/styled';

interface DropdownStyleProps {
  $isOpened: boolean;
}

export const ProfileSection = styled.section`
  cursor: pointer;
  position: relative;
  width: fit-content;
  padding: 0 1rem;
`;

export const ProfileContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const ProfileImageWrapper = styled.div`
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 4rem;
  height: 4rem;

  border-radius: 2rem;
`;

export const ProfileId = styled.p`
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;

export const ArrowIcon = styled.img<DropdownStyleProps>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  width: 2rem;
  height: 2rem;
  transition: transform 0.3s ease-in-out;
`;
