import { DefaultBodyType, http, HttpResponse, StrictRequest } from 'msw';

import endPoint, { REVIEW_GROUP_DATA_API_PARAMS } from '@/apis/endpoints';
import { API_ERROR_MESSAGE, INVALID_REVIEW_PASSWORD_MESSAGE } from '@/constants';
import { getRequestBody } from '@/utils/mockingUtils';

import { reviewLinks } from '../mockData';
import {
  MOCK_AUTH_TOKEN_NAME,
  VALID_REVIEW_REQUEST_CODE,
  VALIDATED_PASSWORD,
  MOCK_LOGIN_TOKEN_NAME,
  MEMBER_REVIEW_GROUP_DATA,
  NONMEMBER_REVIEW_GROUP_DATA,
} from '../mockData/group';

// NOTE: reviewRequestCode 생성 정상 응답
const postDataForReviewRequestCode = () => {
  return http.post(endPoint.postingDataForReviewRequestCode, async ({ request, cookies }) => {
    // request body의 존재 검증
    const bodyResult = await getRequestBody(request);

    if (bodyResult instanceof Error) return HttpResponse.json({ error: bodyResult.message }, { status: 400 });

    const isNonMember = 'groupAccessCode' in bodyResult;

    const reviewRequestCode = isNonMember ? VALID_REVIEW_REQUEST_CODE.nonMember : VALID_REVIEW_REQUEST_CODE.member;

    const { member: memberReviewRequestCode } = VALID_REVIEW_REQUEST_CODE;

    const newReviewLink = {
      revieweeName: '쑤쑤',
      projectName: '리뷰미',
      createdAt: '2025-05-10',
      reviewRequestCode: memberReviewRequestCode,
      reviewCount: 30,
    };

    // 새로 생성된 리뷰 링크를 목 데이터에 추가
    reviewLinks.reviewGroups.push(newReviewLink);

    // 회원용일 경우, credentials과 쿠키 검증
    if (!isNonMember) {
      const isError = request.credentials !== 'include' || !cookies[MOCK_LOGIN_TOKEN_NAME];

      if (isError) {
        return HttpResponse.json({ error: '인증 관련 쿠키를 가져올 수 없습니다' }, { status: 401 });
      }
    }

    return HttpResponse.json(
      {
        reviewRequestCode,
      },
      { status: 200 },
    );
  });
};

const postPassWordValidation = () => {
  return http.post(endPoint.checkingReviewRequestPassword, async ({ request, cookies }) => {
    const bodyResult = await getRequestBody(request);
    if (bodyResult instanceof Error) return HttpResponse.json({ error: bodyResult.message }, { status: 400 });

    // request에 포함된 값들의 검증 시작
    const { reviewRequestCode, groupAccessCode: password } = bodyResult;

    // 유효하지 않은 비밀번호인 경우
    if (password !== VALIDATED_PASSWORD) {
      return HttpResponse.json({ error: INVALID_REVIEW_PASSWORD_MESSAGE }, { status: 401 });
    }

    // reviewRequestCode가 없는 경우
    if (!reviewRequestCode) {
      return HttpResponse.json({ error: API_ERROR_MESSAGE[400] }, { status: 400 });
    }

    // 정상 응답 (유효한 비밀번호)
    //세션 쿠키 생성 (브라우저 창 닫히면 자동 삭제됨, 이미 있으면 생성 하지 않음)
    return new HttpResponse(null, {
      headers: cookies[MOCK_AUTH_TOKEN_NAME]
        ? {}
        : {
            'Set-cookie': `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`,
          },
      status: 204,
    });
  });
};

const handleReviewGroupDataRequest = (request: StrictRequest<DefaultBodyType>) => {
  const url = new URL(request.url);
  const params = new URLSearchParams(url.search);
  const { queryString } = REVIEW_GROUP_DATA_API_PARAMS;

  // 리뷰 그룹 정보에 대한 요청인지 확인
  if (!params.has(queryString.reviewRequestCode)) {
    return HttpResponse.json({ error: '리뷰 요청 코드가 없습니다.' }, { status: 400 });
  }

  // 요청 URL에서 reviewRequestCode 추출
  const reviewRequestCode = params.get(queryString.reviewRequestCode);

  // 유효한 리뷰 요청 코드인지 확인
  if (reviewRequestCode === VALID_REVIEW_REQUEST_CODE.nonMember) {
    return HttpResponse.json(NONMEMBER_REVIEW_GROUP_DATA, { status: 200 });
  }

  if (reviewRequestCode === VALID_REVIEW_REQUEST_CODE.member) {
    return HttpResponse.json(MEMBER_REVIEW_GROUP_DATA, { status: 200 });
  }

  return HttpResponse.json({ error: '잘못된 리뷰 그룹 데이터 요청' }, { status: 404 });
};

/**
 * 리뷰 연결 페이지에서 리뷰이 이름, 프로젝트 이름을 가져오는 목 핸들러
 */
// 예시 출력
const getNonMemberReviewGroupData = () => {
  const { nonMember } = VALID_REVIEW_REQUEST_CODE;
  const nonMemberUrl = endPoint.gettingReviewGroupData(nonMember);

  return http.get(nonMemberUrl, async ({ request }) => {
    return handleReviewGroupDataRequest(request);
  });
};

const getMemberReviewGroupData = () => {
  const { member } = VALID_REVIEW_REQUEST_CODE;
  const memberUrl = endPoint.gettingReviewGroupData(member);

  return http.get(memberUrl, async ({ request }) => {
    return handleReviewGroupDataRequest(request);
  });
};

const groupHandler = [
  postDataForReviewRequestCode(),
  getNonMemberReviewGroupData(),
  getMemberReviewGroupData(),
  postPassWordValidation(),
];

export default groupHandler;
