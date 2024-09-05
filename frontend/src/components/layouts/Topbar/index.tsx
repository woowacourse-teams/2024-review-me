// import UserProfileIcon from '../../../assets/userProfile.svg';
// import { SearchInput } from '../../common';

import PreventDrag from '@/components/common/PreventDrag';

import Logo from './components/Logo';
import SidebarOpenButton from './components/SidebarOpenButton';
import * as S from './styles';

// const USER_SEARCH_PLACE_HOLDER = '사용자를 입력해주세요';

interface TopbarProps {
  openSidebar: () => void;
}
const Topbar = ({ openSidebar }: TopbarProps) => {
  return (
    <S.Layout>
      <S.Container>
        {/* <SidebarOpenButton openSidebar={openSidebar} /> */}
        <PreventDrag>
          <Logo />
        </PreventDrag>
      </S.Container>
      <S.Container>
        {/* <SearchInput $width="30rem" $height="3.6rem" placeholder={USER_SEARCH_PLACE_HOLDER} /> */}
        {/* <S.UserProfile src={UserProfileIcon} alt="로그인한 사용자 깃허브 프로필" /> */}
      </S.Container>
    </S.Layout>
  );
};

export default Topbar;
