import { atom } from 'recoil';

import { ATOM_KEY } from '../keys';

export interface URLGeneratorForm {
  revieweeName: string;
  projectName: string;
  password: string;
}

export const URLGeneratorFormField = {
  revieweeName: 'revieweeName',
  projectName: 'projectName',
  password: 'password',
} as const;

export type URLGeneratorFormField = keyof typeof URLGeneratorFormField;

// NOTE: 컨벤션상 boolean 변수는 is~로 시작해야 하지만, 객체의 특정 속성을 찾아 값을 갱신할 때
// urlGeneratorFormAtom의 속성 이름을 그대로 사용하는 게 더 경제적이었기 때문에 똑같이 사용중 (논의 필요)
export interface URLGeneratorFormValidation {
  revieweeName: boolean;
  projectName: boolean;
  password: boolean;
}

export const urlGeneratorFormAtom = atom<URLGeneratorForm>({
  key: ATOM_KEY.urlGeneratorForm,
  default: {
    revieweeName: '',
    projectName: '',
    password: '',
  },
});

export const urlGeneratorFormValidationAtom = atom<URLGeneratorFormValidation>({
  key: ATOM_KEY.urlGeneratorFormValidation,
  default: {
    revieweeName: false,
    projectName: false,
    password: false,
  },
});
