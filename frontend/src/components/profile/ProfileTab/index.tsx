import { ProfileTabElement } from '@/types/profile';

import * as S from './styles';

interface ProfileTabProps {
  items: ProfileTabElement[];
}

const ProfileTab = ({ items }: ProfileTabProps) => {
  return (
    <S.ProfileTabContainer>
      {items.map((item) => {
        switch (item.elementType) {
          case 'readonly':
            return <S.ReadonlyItemWrapper>{item.content}</S.ReadonlyItemWrapper>;
          case 'action':
            return <S.ActionItemWrapper onClick={item.handleClick}>{item.content}</S.ActionItemWrapper>;
          case 'divider':
            return <S.Divider />;
        }
      })}
    </S.ProfileTabContainer>
  );
};

export default ProfileTab;
