import DownArrowIcon from '@/assets/downArrow.svg';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ImgWithSkeleton } from '@/components/skeleton';
import { SocialType } from '@/types/profile';

import ProfileTab from '../ProfileTab';
import useProfile from '../ProfileTab/hooks/useProfile';

import * as S from './styles';

interface ProfileInfoProps {
  profileImageSrc?: string;
  profileId: string;
  socialType: SocialType;
}

const ProfileInfo = ({ profileImageSrc, profileId, socialType }: ProfileInfoProps) => {
  const { isOpened, containerRef, handleContainerClick } = useProfile();

  return (
    <S.ProfileSection ref={containerRef}>
      <UndraggableWrapper>
        <S.ProfileContainer onClick={handleContainerClick}>
          <S.ProfileImageWrapper>
            {profileImageSrc && (
              <ImgWithSkeleton imgHeight="inherit" imgWidth="inherit">
                <img src={profileImageSrc} alt="프로필 사진" />
              </ImgWithSkeleton>
            )}
          </S.ProfileImageWrapper>
          <S.ProfileId>{profileId}</S.ProfileId>
          <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
        </S.ProfileContainer>
      </UndraggableWrapper>
      {isOpened && <ProfileTab profileId={profileId} socialType={socialType} />}
    </S.ProfileSection>
  );
};

export default ProfileInfo;
