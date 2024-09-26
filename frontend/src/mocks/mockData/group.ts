import { ReviewGroupData } from '@/types';

export const CREATED_REVIEW_REQUEST_CODE = {
  reviewRequestCode: 'mocked-reviewRequestCode',
};

export const VALIDATED_PASSWORD = '1234';

export const MOCK_AUTH_TOKEN_NAME = 'mockAuthToken';
/**
 * 리뷰 연결 페이지에서 사용하는 리뷰 그룹 정보
 */
export const REVIEW_GROUP_DATA: ReviewGroupData = {
  revieweeName: '바다',
  projectName: '2024-review-me',
};

/**리뷰 연결 페이지에서 유효한 reviewRequestCode */
export const VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE = `ABCD1234`;
