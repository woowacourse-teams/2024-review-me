import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { VALID_REVIEW_REQUEST_CODE } from '../mockData';

import { authorizeWithCookie } from './cookies';

const postMockHighlight = () => {
  const nonMemberUrl = endPoint.postingHighlight(VALID_REVIEW_REQUEST_CODE.nonMember);
  const memberUrl = endPoint.postingHighlight(VALID_REVIEW_REQUEST_CODE.member);
  const targetUrl = new RegExp(`^(${nonMemberUrl}|${memberUrl})`);

  return http.post(targetUrl, ({ cookies }) => {
    return authorizeWithCookie(cookies, () => HttpResponse.json({ status: 200 }));
  });
};

const highlightHandler = [postMockHighlight()];
export default highlightHandler;
