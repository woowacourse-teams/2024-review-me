import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ProfileInfo from '@/components/profile/ProfileInfo';
import { UserProfile } from '@/types/profile';

import Logo from './components/Logo';
import * as S from './styles';

interface TopbarProps {
  isUserLoggedIn: boolean;
  $hideBorderBottom: boolean;
  userProfile: UserProfile | null;
}

const Topbar = ({ isUserLoggedIn, $hideBorderBottom, userProfile }: TopbarProps) => {
  return (
    <S.Layout $hideBorderBottom={$hideBorderBottom}>
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
