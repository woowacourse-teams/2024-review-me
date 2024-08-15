import { getIsValidGroupAccessCodeApi } from '@/apis/group';

describe('getIsValidGroupAccessCodeApi', () => {
  it('유효한 확인 코드인 경우 유효성 여부를 참으로 반환한다', async () => {
    const validGroupAccessCode = 'group20';

    const isValid = await getIsValidGroupAccessCodeApi(validGroupAccessCode);

    expect(isValid).toBe(true);
  });
});
