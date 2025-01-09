import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ProfileTabElement, SocialType } from '@/types/profile';

import ActionItem from './components/ActionItem';
import Divider from './components/Divider';
import ReadonlyItem from './components/ReadonlyItem';
import useProfileTabElements from './hooks/useProfileTabElements';
import * as S from './styles';

interface ProfileTabProps {
  profileId: string;
  socialType: SocialType;
}

const ProfileTab = ({ socialType, profileId }: ProfileTabProps) => {
  const { profileTabElements } = useProfileTabElements({ profileId, socialType });

  const renderProfileTabItem = (item: ProfileTabElement) => {
    switch (item.elementType) {
      case 'readonly':
        return (
          <ReadonlyItem
            key={item.elementId}
            isDisplayedOnlyMobile={item.isDisplayedOnlyMobile}
            content={item.content!}
          />
        );
      case 'action':
        return (
          <ActionItem
            key={item.elementId}
            handleItemClick={item.handleClick!}
            isDisplayedOnlyMobile={item.isDisplayedOnlyMobile}
            content={item.content!}
          />
        );
      case 'divider':
        return <Divider key={item.elementId} isDisplayedOnlyMobile={item.isDisplayedOnlyMobile} />;
    }
  };

  return (
    <S.ProfileTabContainer>
      <UndraggableWrapper>{profileTabElements.map((element) => renderProfileTabItem(element))}</UndraggableWrapper>
    </S.ProfileTabContainer>
  );
};

export default ProfileTab;
