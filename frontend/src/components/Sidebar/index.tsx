import * as S from './styles';
import MenuIcon from '../../assets/menu.svg';
import LogoIcon from '../../assets/logo.svg';

import { useLocation, Link } from 'react-router-dom';

const Sidebar = () => {
  const location = useLocation();

  return (
    <S.Sidebar>
      <S.Top>
        <button>
          <img src={MenuIcon} alt="메뉴" />
        </button>
        <button>
          <img src={LogoIcon} alt="로고" />
        </button>
      </S.Top>
      <S.UserInfo>
        <S.ProfileImage />
        <S.ProfileId>chysis</S.ProfileId>
      </S.UserInfo>
      <S.MenuList>
        <S.MenuItem selected={location.pathname === '/user/review-writing'}>
          <Link to="/user/review-writing">리뷰 작성하기</Link>
        </S.MenuItem>
        <S.MenuItem selected={false}>요청받은 리뷰</S.MenuItem>
        <S.MenuItem selected={location.pathname === '/user/detailed-review'}>
          <Link to="/user/detailed-review">상세 리뷰 보기</Link>
        </S.MenuItem>
        <S.MenuItem selected={false}>통계 보기</S.MenuItem>
      </S.MenuList>
    </S.Sidebar>
  );
};

export default Sidebar;
