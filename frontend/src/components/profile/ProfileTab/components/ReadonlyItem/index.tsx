import { ProfileTabElementContent } from '@/types/profile';

import * as S from './styles';

interface ReadonlyItemProps {
  isDisplayedOnlyMobile: boolean;
  content: ProfileTabElementContent;
}

const ReadonlyItem = ({ isDisplayedOnlyMobile, content }: ReadonlyItemProps) => {
  return (
    <S.ReadonlyItemContainer $isDisplayedOnlyMobile={isDisplayedOnlyMobile}>
      <img src={content.icon.src} alt={content.icon.alt} />
      <S.ItemText>{content.text}</S.ItemText>
    </S.ReadonlyItemContainer>
  );
};

export default ReadonlyItem;
