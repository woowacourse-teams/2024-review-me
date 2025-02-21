import { ReviewGroupData } from '@/types';

import { MOCK_USER_PROFILE } from './userProfileData';

export const VALIDATED_PASSWORD = '1234';

export const MOCK_AUTH_TOKEN_NAME = 'mockAuthToken';
export const MOCK_LOGIN_TOKEN_NAME = 'mockLoginToken';

export const nonMemberOnlyCookie = [MOCK_AUTH_TOKEN_NAME];
export const memberOnlyCookie = [MOCK_LOGIN_TOKEN_NAME];
export const bothCookie = [MOCK_AUTH_TOKEN_NAME, MOCK_LOGIN_TOKEN_NAME];

/**
 * 리뷰 연결 페이지에서 사용하는 리뷰 그룹 정보
 */

// 변수명이 카멜 케이스인 이유는 회원/비회원에 따라 revieweeId가 달라지기 때문에 reviewRequestCode보고 판단해서 회원이면 number, 비회원이면 null로 설정
export const MEMBER_REVIEW_GROUP_DATA: ReviewGroupData = {
  revieweeId: MOCK_USER_PROFILE.memberId, //💡 본인이 만든 리뷰 링크가 아닐 때 토스트 기능을 확인하고 싶다면 다른 리뷰 아이디를 넣어서 확인해보세요
  revieweeName: '바다',
  projectName: '2024-review-me',
};

export const NONMEMBER_REVIEW_GROUP_DATA: ReviewGroupData = {
  revieweeId: null,
  revieweeName: '손님',
  projectName: '2024-review-me',
};

/**리뷰 연결 페이지에서 유효한 reviewRequestCode */
export const VALID_REVIEW_REQUEST_CODE = {
  nonMember: `ABCD1234`,
  member: 'MEMBER1234',
};
