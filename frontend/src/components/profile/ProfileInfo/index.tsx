import { useRef, useState } from 'react';

import DownArrowIcon from '@/assets/downArrow.svg';
import GitHubIcon from '@/assets/github.svg';

import ProfileTab from '../ProfileTab';

import * as S from './styles';

interface ProfileInfoProps {
  profileImageSrc?: string;
  profileId: string;
}

const ProfileInfo = ({ profileImageSrc, profileId }: ProfileInfoProps) => {
  const [isOpened, setIsOpened] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);

  const handleContainerClick = () => {
    setIsOpened((prev) => !prev);
  };

  return (
    <S.ProfileSection>
      <S.ProfileContainer onClick={handleContainerClick} ref={containerRef}>
        <S.ProfileImageWrapper>
          <img src={profileImageSrc || GitHubIcon} alt="프로필 사진" />
        </S.ProfileImageWrapper>
        <S.ProfileId>{profileId}</S.ProfileId>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.ProfileContainer>
      {isOpened && <ProfileTab />}
    </S.ProfileSection>
  );
};

export default ProfileInfo;
