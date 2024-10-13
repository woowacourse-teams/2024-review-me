import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

const postMockHighlight = () =>
  http.post(endPoint.postingHighlight, async () => {
    return HttpResponse.json({ status: 200 });
  });

const highlightHandler = [postMockHighlight()];
export default highlightHandler;
