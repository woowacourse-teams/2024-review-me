import { ProfileTabElementContent } from '@/types/profile';

import * as S from './styles';

interface ActionItemProps {
  isDisplayedOnlyMobile: boolean;
  content: ProfileTabElementContent;
  handleItemClick: () => void;
}

const ActionItem = ({ isDisplayedOnlyMobile, content, handleItemClick }: ActionItemProps) => {
  return (
    <S.ActionItemContainer $isDisplayedOnlyMobile={isDisplayedOnlyMobile} onClick={handleItemClick}>
      <img src={content.icon.src} alt={content.icon.alt} />
      <S.ItemText>{content.text}</S.ItemText>
    </S.ActionItemContainer>
  );
};

export default ActionItem;
