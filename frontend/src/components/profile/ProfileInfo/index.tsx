import DownArrowIcon from '@/assets/downArrow.svg';
import GitHubIcon from '@/assets/github.svg';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { SocialType } from '@/types/profile';

import ProfileTab from '../ProfileTab';
import useProfile from '../ProfileTab/hooks/useProfile';
import useProfileTabElements from '../ProfileTab/hooks/useProfileTabElements';

import * as S from './styles';

interface ProfileInfoProps {
  profileImageSrc?: string;
  profileId: string;
  socialType: SocialType;
}

const ProfileInfo = ({ profileImageSrc, profileId, socialType }: ProfileInfoProps) => {
  const { isOpened, containerRef, handleContainerClick } = useProfile();
  const { profileTabElements } = useProfileTabElements({ profileId, socialType });

  return (
    <S.ProfileSection ref={containerRef}>
      <UndraggableWrapper>
        <S.ProfileContainer onClick={handleContainerClick}>
          <S.ProfileImageWrapper>
            <img src={profileImageSrc || GitHubIcon} alt="프로필 사진" />
          </S.ProfileImageWrapper>
          <S.ProfileId>{profileId}</S.ProfileId>
          <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
        </S.ProfileContainer>
      </UndraggableWrapper>
      {isOpened && <ProfileTab items={profileTabElements} />}
    </S.ProfileSection>
  );
};

export default ProfileInfo;
