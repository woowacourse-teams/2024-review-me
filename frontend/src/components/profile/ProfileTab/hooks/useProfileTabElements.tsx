import { useNavigate } from 'react-router';

import GitHubIcon from '@/assets/github.svg';
import LogoutIcon from '@/assets/logout.svg';
import MenuIcon from '@/assets/menu.svg';
import OpenedBookIcon from '@/assets/openedBook.svg';
import UserIcon from '@/assets/user.svg';
import { ProfileTabElement, SocialType } from '@/types/profile';

interface UseProfileTabElementsProps {
  profileId: string;
  socialType: SocialType;
}

const useProfileTabElements = ({ profileId, socialType }: UseProfileTabElementsProps) => {
  const navigate = useNavigate();

  const handleReviewLinkControl = () => {
    // 리뷰 링크 관리 페이지로 이동
    console.log('리뷰 링크 관리 클릭');
  };

  const handleCheckWrittenReviews = () => {
    // 작성한 리뷰 확인 페이지로 이동
    console.log('작성한 리뷰 확인 클릭');
  };

  const handleLogout = () => {
    // 로그아웃 로직
    console.log('로그아웃 클릭');
  };

  const profileTabElements: ProfileTabElement[] = [
    {
      elementType: 'readonly',
      isDisplayedOnlyMobile: false,
      content: socialType === 'github' && (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <img src={GitHubIcon} alt="소셜 아이콘" />
          <span>GitHub 계정</span>
        </div>
      ),
    },
    {
      elementType: 'readonly',
      isDisplayedOnlyMobile: true,
      content: (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <img src={UserIcon} alt="사람 아이콘" />
          <span>{profileId}</span>
        </div>
      ),
    },
    {
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <img src={MenuIcon} alt="메뉴 아이콘" />
          <span>리뷰 링크 관리</span>
        </div>
      ),
      handleClick: handleReviewLinkControl,
    },
    {
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <img src={OpenedBookIcon} alt="펼쳐진 책 아이콘" />
          <span>작성한 리뷰 확인</span>
        </div>
      ),
      handleClick: handleCheckWrittenReviews,
    },
    {
      elementType: 'divider',
      isDisplayedOnlyMobile: false,
    },
    {
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: (
        <div style={{ display: 'flex', gap: '1rem' }}>
          <img src={LogoutIcon} alt="펼쳐진 책 아이콘" />
          <span>로그아웃</span>
        </div>
      ),
      handleClick: handleLogout,
    },
  ];

  return { profileTabElements };
};

export default useProfileTabElements;
