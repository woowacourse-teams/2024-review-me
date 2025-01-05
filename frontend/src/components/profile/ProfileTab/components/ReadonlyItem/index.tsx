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
      <span>{content.text}</span>
    </S.ReadonlyItemContainer>
  );
};

export default ReadonlyItem;
