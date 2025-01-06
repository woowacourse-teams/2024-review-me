import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ProfileTabElement } from '@/types/profile';

import ActionItem from './components/ActionItem';
import Divider from './components/Divider';
import ReadonlyItem from './components/ReadonlyItem';
import * as S from './styles';

interface ProfileTabProps {
  items: ProfileTabElement[];
}

const ProfileTab = ({ items }: ProfileTabProps) => {
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
      <UndraggableWrapper>{items.map((item) => renderProfileTabItem(item))}</UndraggableWrapper>
    </S.ProfileTabContainer>
  );
};

export default ProfileTab;
