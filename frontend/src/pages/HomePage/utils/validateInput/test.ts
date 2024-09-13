import { isNotEmptyInput, isAlphanumeric, isWithinLengthRange } from '@/pages/HomePage/utils/validateInput';

describe('isNotEmptyInput', () => {
  test.each(['abc', '123', '!@#', '하이'])('빈 문자열이 아닐 경우 true를 반환한다. (input: %s)', (input) => {
    expect(isNotEmptyInput(input)).toBe(true);
  });

  test('빈 문자열인 경우 false를 반환한다.', () => {
    expect(isNotEmptyInput('')).toBe(false);
  });
});

describe('isAlphanumeric', () => {
  test.each(['abc', 'ABC', '123', 'abc123', 'ABC123'])(
    '영문 대소문자 및 숫자만 포함된 입력인 경우 true를 반환한다. (input: %s)',
    (input) => {
      expect(isAlphanumeric(input)).toBe(true);
    },
  );

  test.each(['abc!', '123@', 'abc123#', '하이'])(
    '영문 대소문자 및 숫자, 공백 외의 입력이 포함된 경우 false를 반환한다. (input: %s)',
    (input) => {
      expect(isAlphanumeric(input)).toBe(false);
    },
  );
});

describe('isWithinLengthRange', () => {
  test.each([
    ['abc', 5],
    ['12345', 5],
    ['a', 5],
  ])('문자열 길이가 0 이상, %d 이하인 경우 true를 반환한다. (input: %s)', (input, end) => {
    expect(isWithinLengthRange(input, end)).toBe(true);
  });
  test.each([
    ['abc', 5, 3],
    ['12345', 5, 3],
  ])('문자열 길이가 %d 이상 %d 이하인 경우 true를 반환한다. (input: %s)', (input, end, start) => {
    expect(isWithinLengthRange(input, end, start)).toBe(true);
  });

  test.each([
    ['abcdef', 5],
    ['longtext', 7],
  ])('문자열 길이가 최대값을 초과하는 경우 false를 반환한다. (input: %s)', (input, end) => {
    expect(isWithinLengthRange(input, end)).toBe(false);
  });

  test.each([
    ['ab', 5, 3],
    ['abcdef', 5, 2],
  ])(
    '문자열 길이가 최소 범위에 미치지 못하는 경우 false를 반환한다. (input: %s, start: %d, end: %d)',
    (input, end, start) => {
      expect(isWithinLengthRange(input, end, start)).toBe(false);
    },
  );
});
