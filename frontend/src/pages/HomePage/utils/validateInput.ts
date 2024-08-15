export const isNotEmptyInput = (input: string) => {
  return input !== '';
};

export const isAlphanumeric = (input: string) => {
  const alphanumericRegex = /^[A-Za-z0-9]*$/;
  return alphanumericRegex.test(input);
};

export const isWithinLengthRange = (input: string, end: number, start: number = 0) => {
  const length = input.length;
  return length >= start && length <= end;
};

export const MAX_VALID_REVIEW_GROUP_DATA_INPUT = 50;

export const isValidReviewGroupDataInput = (input: string) => {
  return isNotEmptyInput(input) && isWithinLengthRange(input, MAX_VALID_REVIEW_GROUP_DATA_INPUT);
};

export const MIN_PASSWORD_INPUT = 4;
export const MAX_PASSWORD_INPUT = 20;

export const isValidPasswordInput = (input: string) => {
  return (
    isNotEmptyInput(input) &&
    isAlphanumeric(input) &&
    isWithinLengthRange(input, MAX_PASSWORD_INPUT, MIN_PASSWORD_INPUT)
  );
};
