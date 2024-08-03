import { atom } from 'recoil';

import { ATOM_KEY } from '../keys';
// NOTE: atom.ts가 아닌 index.ts를 한 이유? atom.ts이면 index.ts를 만들어줘야하는데 현재 상황 상, atom.ts와 index.ts를 둘 다 만들 필요를 느끼지 못했어요.  3차-1주차 시현을 위해 임시로 groupAccessCode를 쓰고 깃헙 로그인 시 jwt같은 기능을 사용할 수 있어서 현재는 index.ts로 만들었습니다.
export const groupAccessCodeAtom = atom<string | null>({
  key: ATOM_KEY.groupAccessCodeAtom,
  default: '1234',
});
