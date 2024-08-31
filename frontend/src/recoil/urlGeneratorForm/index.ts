import { atom } from 'recoil';

import { URLGeneratorForm, URLGeneratorFormValidation } from '@/types';

import { ATOM_KEY } from '../keys';

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
