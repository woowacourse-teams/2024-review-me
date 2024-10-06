import styled from '@emotion/styled';

export const DropdownContainer = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;

  width: 24rem;
  margin-bottom: 1rem;
`;

export const DropdownButton = styled.button`
  display: flex;
  gap: 1rem;
  justify-content: space-between;

  width: 100%;
  padding: 1rem;

  background-color: ${({ theme }) => theme.colors.white};
  border: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  &:hover {
    background-color: ${({ theme }) => theme.colors.lightGray};
  }
`;

export const SelectedOption = styled.p`
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const ArrowIcon = styled.img<{ $isOpened: boolean }>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  transition: transform 0.3s ease-in-out;
`;

export const ItemContainer = styled.ul`
  position: absolute;
  z-index: ${({ theme }) => theme.zIndex.dropdown};
  top: 100%;

  overflow: hidden;

  width: 100%;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const DropdownItem = styled.li`
  cursor: pointer;
  user-select: none;

  display: flex;
  align-items: center;

  width: 100%;
  height: 4rem;
  padding: 0 1rem;

  background-color: ${({ theme }) => theme.colors.white};

  &:hover {
    background-color: ${({ theme }) => theme.colors.lightGray};
  }
`;
