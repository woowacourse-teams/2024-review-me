import { PageName } from '@/types';

export const HIGHLIGHT_EVENT_NAME = {
  addHighlightByDrag: '드래그를 통한 형광펜 추가',
  removeHighlightByDrag: '드래그를 통한 형광펜 삭제',
  removeHighlightByLongPress: '길게 눌러서 형광펜 영역 삭제',
  openHighlightEditor: '형광펜 에디터 열기',
};

export const COLLECTION_EVENT_NAME = {
  switchCardSection: '리뷰 모아보기-리뷰 카드 섹션 변경',
};

export const COLLECTION_LIST_SWITCH_EVENT_NAME = {
  collection: '스위치 버튼으로 리뷰 모아보기 열기',
  list: '스위치 버튼으로 리뷰 목록 열기',
};

export const PAGE_VISITED_EVENT_NAME: { [key in Exclude<PageName, undefined>]: string } = {
  home: '[page] 홈 페이지',
  reviewZone: '[page] 리뷰 연결 페이지',
  reviewList: '[page] 리뷰 목록 페이지',
  reviewCollection: '[page] 리뷰 모아보기 페이지',
  detailedReview: '[page] 리뷰 상세 보기 페이지',
  reviewWriting: '[page] 리뷰 작성 페이지',
  reviewWritingComplete: '[page] 리뷰 작성 완료 페이지',
};

export const REVIEW_WRITING_EVENT_NAME = {
  submitReview: '리뷰 제출',
};
