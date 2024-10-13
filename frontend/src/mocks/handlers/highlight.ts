import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

import { MOCK_AUTH_TOKEN_NAME } from '../mockData';

const postMockHighlight = () =>
  http.post(endPoint.postingHighlight, async ({ cookies }) => {
    if (!cookies[MOCK_AUTH_TOKEN_NAME]) {
      return HttpResponse.json({ error: '인증 관련 쿠키 없음' }, { status: 401 });
    }

    return HttpResponse.json({ status: 200 });
  });

const highlightHandler = [postMockHighlight()];

export default highlightHandler;
