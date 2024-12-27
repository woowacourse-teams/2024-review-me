export type SocialType = 'github';

export type ProfileTabElementType = 'readonly' | 'action' | 'divider';

export interface ProfileTabElement {
  elementType: ProfileTabElementType;
  isDisplayedOnlyMobile: boolean; // true: 모바일만, false: 전체
  content?: React.ReactNode; // divider 제외 지정
  handleClick?: () => void; // action일 때 지정
}
