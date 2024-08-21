import { atom } from 'recoil';

import { ATOM_KEY } from '../keys';

/**
 * 리뷰 URL 뒷부분의 난수에 해당하는 reviewRequestCode
 */
export const reviewRequestCodeAtom = atom<string>({
  key: ATOM_KEY.reviewWritingForm.reviewRequestCodeAtom,
  default: '',
});
