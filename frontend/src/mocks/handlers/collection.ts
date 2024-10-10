import { http, HttpResponse } from 'msw';

import endPoint from '@/apis/endpoints';

const postMockHighlight = () =>
  http.post(endPoint.postingHighlight, async () => {
    return HttpResponse.json({ status: 200 });
  });

const collectionHandler = [postMockHighlight()];
export default collectionHandler;
