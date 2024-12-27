import { ProfileTabElement } from '@/types/profile';

import * as S from './styles';

interface ProfileTabProps {
  items: ProfileTabElement[];
}

const ProfileTab = ({ items }: ProfileTabProps) => {
  return (
    <S.ProfileTabContainer>
      {items.map((item, index) => {
        switch (item.elementType) {
          case 'readonly':
            return (
              <S.ReadonlyItemWrapper
                key={`${item.elementType}_${index}`}
                $isDisplayedOnlyMobile={item.isDisplayedOnlyMobile}
              >
                {item.content}
              </S.ReadonlyItemWrapper>
            );
          case 'action':
            return (
              <S.ActionItemWrapper
                key={`${item.elementType}_${index}`}
                onClick={item.handleClick}
                $isDisplayedOnlyMobile={item.isDisplayedOnlyMobile}
              >
                {item.content}
              </S.ActionItemWrapper>
            );
          case 'divider':
            return (
              <S.Divider key={`${item.elementType}_${index}`} $isDisplayedOnlyMobile={item.isDisplayedOnlyMobile} />
            );
        }
      })}
    </S.ProfileTabContainer>
  );
};

export default ProfileTab;
