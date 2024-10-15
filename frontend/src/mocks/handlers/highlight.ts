import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { authorizeWithCookie } from './cookies';

const postMockHighlight = () =>
  http.post(endPoint.postingHighlight, ({ cookies }) => {
    return authorizeWithCookie(cookies, () => HttpResponse.json({ status: 200 }));
  });

const highlightHandler = [postMockHighlight()];
export default highlightHandler;
