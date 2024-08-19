import { ReviewGroupData } from '@/types';

export const CREATED_GROUP_DATA = {
  reviewRequestCode: 'mocked-reviewRequestCode',
  groupAccessCode: 'mocked-groupAccessCode',
};

export const INVALID_GROUP_ACCESS_CODE = {
  type: 'about:blank',
  title: 'Bad Request',
  status: 400,
  detail: '올바르지 않은 확인 코드입니다.',
  instance: '/reviews',
};

/**
 * 리뷰 연결 페이지에서 사용하는 리뷰 그룹 정보
 */
export const REVIEW_GROUP_DATA: ReviewGroupData = {
  revieweeName: '바다',
  projectName: '2024-review-me',
};

/**리뷰 연결 페이지에서 유효한 reviewRequestCode */
export const VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE = `ABCD1234`;
