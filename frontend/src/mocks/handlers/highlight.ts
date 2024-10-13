import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { withAuthCookie } from './cookies';

const postMockHighlight = () =>
  http.post(endPoint.postingHighlight, ({ cookies }) => {
    return withAuthCookie(cookies, () => HttpResponse.json({ status: 200 }));
  });

const highlightHandler = [postMockHighlight()];
export default highlightHandler;
