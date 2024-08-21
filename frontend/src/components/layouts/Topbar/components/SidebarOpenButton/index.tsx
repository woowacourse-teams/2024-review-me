import HamburgerIcon from '../../../../../assets/menu.svg';

import * as S from './styles';

interface SidebarOpenButton {
  openSidebar: () => void;
}

const SidebarOpenButton = ({ openSidebar }: SidebarOpenButton) => {
  return (
    <S.HamburgerButton onClick={openSidebar}>
      <img src={HamburgerIcon} alt="사이드바 오픈 버튼" />
    </S.HamburgerButton>
  );
};

export default SidebarOpenButton;
