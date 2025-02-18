import { ReviewGroupData } from '@/types';

export const VALIDATED_PASSWORD = '1234';

export const MOCK_AUTH_TOKEN_NAME = 'mockAuthToken';
/**
 * 리뷰 연결 페이지에서 사용하는 리뷰 그룹 정보
 */

// 변수명이 카멜 케이스인 이유는 회원/비회원에 따라 revieweeId가 달라지기 때문에 reviewRequestCode보고 판단해서 회원이면 number, 비회원이면 null로 설정
export const REVIEW_GROUP_DATA: ReviewGroupData = {
  revieweeId: 1,
  revieweeName: '바다',
  projectName: '2024-review-me',
};

/**리뷰 연결 페이지에서 유효한 reviewRequestCode */
export const VALID_REVIEW_REQUEST_CODE = {
  nonMember: `ABCD1234`,
  member: 'MEMBER1234',
};
