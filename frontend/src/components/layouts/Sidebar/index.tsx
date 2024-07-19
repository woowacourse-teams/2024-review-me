import { useLocation, Link } from 'react-router-dom';

import CloseIcon from '../../../assets/close.svg';
import LogoIcon from '../../../assets/logo.svg';
import { PAGE } from '../../../constants';

import * as S from './styles';

const PATH = {
  myPage: '/user/mypage',
  reviewWriting: '/user/review-writing',
  allReview: '/user/all-review',
  detailedReview: '/user/detailed-review',
  reviewGroupManagement: '/user/review-group-management',
};


  const location = useLocation();

  const menuItems = [
    { path: PATH.myPage, label: PAGE.myPage },
    { path: PATH.reviewWriting, label: PAGE.reviewWriting },
    { path: PATH.allReview, label: PAGE.allReview },
    { path: PATH.detailedReview, label: PAGE.detailedReview },
    { path: PATH.reviewGroupManagement, label: PAGE.reviewGroupManagement },
  ];

  return (
    <S.Sidebar $isOpen={isSidebarOpen}>
      <S.Top>
        <button>
          <S.LogoIcon src={LogoIcon} alt="로고" />
        </button>
        <button onClick={closeSidebar}>
          <S.CloseIcon src={CloseIcon} alt="닫기 버튼" />
        </button>
      </S.Top>
      <S.MenuList>
        {menuItems.map((item) => (
          <S.MenuItem key={item.path} selected={location.pathname === item.path}>
            <Link to={item.path}>{item.label}</Link>
          </S.MenuItem>
        ))}
      </S.MenuList>
    </S.Sidebar>
  );
};

export default Sidebar;
