export type SocialType = 'github';

export type ProfileTabElementType = 'readonly' | 'action' | 'divider';

interface ContentIcon {
  src: string;
  alt: string;
}
export interface ProfileTabElementContent {
  icon: ContentIcon;
  text: string;
}
export interface ProfileTabElement {
  elementId: string;
  elementType: ProfileTabElementType;
  isDisplayedOnlyMobile: boolean; // true: 모바일만, false: 전체
  content?: ProfileTabElementContent; // divider 제외 지정
  handleClick?: () => void; // action일 때 지정
}
