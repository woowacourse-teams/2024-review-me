import { useLocation } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ProfileInfo from '@/components/profile/ProfileInfo';
import { ROUTE } from '@/constants';
import { useGetUserProfile } from '@/hooks/oAuth';

import Logo from './components/Logo';
import * as S from './styles';

const Topbar = () => {
  const { pathname } = useLocation();
  const { userProfile, isUserLoggedIn } = useGetUserProfile();
  const $hasNavigationTab = [ROUTE.reviewLinks, ROUTE.writtenReview].includes(pathname);

  return (
    <S.Layout $hasNavigationTab={$hasNavigationTab}>
      <S.Container>
        <UndraggableWrapper>
          <Logo />
        </UndraggableWrapper>
        {isUserLoggedIn && userProfile && (
          <ProfileInfo
            profileId={userProfile.nickname}
            profileImageSrc={userProfile.profileImageUrl}
            socialType="github"
          />
        )}
      </S.Container>
    </S.Layout>
  );
};

export default Topbar;
