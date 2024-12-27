import { REVIEW_URL_GENERATOR_FORM_VALIDATION } from '@/constants';

export const isNotEmptyInput = (input: string) => {
  return input.trim() !== '';
};

export const isAlphanumeric = (input: string) => {
  const alphanumericRegex = /^[A-Za-z0-9]*$/;
  return alphanumericRegex.test(input);
};

export const isWithinLengthRange = (input: string, end: number, start: number = 0) => {
  const length = input.length;
  return length >= start && length <= end;
};

export const isValidReviewGroupDataInput = (input: string) => {
  const { min, max } = REVIEW_URL_GENERATOR_FORM_VALIDATION.groupData;
  return isNotEmptyInput(input) && isWithinLengthRange(input, max, min);
};

export const isValidPasswordInput = (input: string) => {
  const { min, max } = REVIEW_URL_GENERATOR_FORM_VALIDATION.password;
  return isNotEmptyInput(input) && isAlphanumeric(input) && isWithinLengthRange(input, max, min);
};
