export const isNotEmptyInput = (input: string) => {
  return input !== '';
};

export const isAlphanumeric = (input: string) => {
  const alphanumericRegex = /^[A-Za-z0-9]*$/;
  return alphanumericRegex.test(input);
};

export const isWithinMaxLength = (input: string, length: number) => {
  return input.length <= length;
};

const MAX_VALID_REVIEW_GROUP_DATA_INPUT = 50;

export const isValidReviewGroupDataInput = (input: string) => {
  return isNotEmptyInput(input) && isWithinMaxLength(input, MAX_VALID_REVIEW_GROUP_DATA_INPUT);
};

const MAX_GROUP_ACCESS_CODE_INPUT = 62;

export const isValidAccessCodeInput = (input: string) => {
  return isNotEmptyInput(input) && isAlphanumeric(input) && isWithinMaxLength(input, MAX_GROUP_ACCESS_CODE_INPUT);
};
