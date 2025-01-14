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
      elementId: 'socialType',
      elementType: 'readonly',
      isDisplayedOnlyMobile: false,
      content:
        // 다른 소셜 타입 추가 시 리팩토링
        socialType === 'github'
          ? { icon: { src: GitHubIcon, alt: '' }, text: 'GitHub 계정' }
          : { icon: { src: '', alt: '' }, text: '' },
    },
    {
      elementId: 'profileId',
      elementType: 'readonly',
      isDisplayedOnlyMobile: true,
      content: { icon: { src: UserIcon, alt: '' }, text: profileId },
    },
    {
      elementId: 'reviewLinkControlButton',
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: { icon: { src: MenuIcon, alt: '' }, text: '리뷰 링크 관리' },
      handleClick: handleReviewLinkControl,
    },
    {
      elementId: 'checkWrittenReviewsButton',
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: { icon: { src: OpenedBookIcon, alt: '' }, text: '작성한 리뷰 확인' },
      handleClick: handleCheckWrittenReviews,
    },
    {
      elementId: 'divider',
      elementType: 'divider',
      isDisplayedOnlyMobile: false,
    },
    {
      elementId: 'logoutButton',
      elementType: 'action',
      isDisplayedOnlyMobile: false,
      content: { icon: { src: LogoutIcon, alt: '' }, text: '로그아웃' },
      handleClick: handleLogout,
    },
  ];

  return { profileTabElements };
};

export default useProfileTabElements;
