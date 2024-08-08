export const CREATED_GROUP_DATA = {
  reviewRequestCode: 'mocked-reviewRequestCode',
  groupAccessCode: 'mocked-groupAccessCode',
};

export const INVALID_GROUP_ACCESS_CODE = {
  type: 'about:blank',
  title: 'Bad Request',
  status: 400,
  detail: '올바르지 않은 확인 코드입니다.',
  instance: '/reviews',
};
