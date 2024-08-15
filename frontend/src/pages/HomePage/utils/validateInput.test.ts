import { isNotEmptyInput, isAlphanumeric, isWithinMaxLength } from './validateInput';

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

describe('isWithinMaxLength', () => {
  const maxLength = 5;
  test('지정한 길이 이하의 입력인 경우 true를 반환한다.', () => {
    const inputWithLength5 = '12345';
    expect(isWithinMaxLength(inputWithLength5, maxLength)).toBe(true);
  });

  test('지정한 길이를 초과하는 입력인 경우 false를 반환한다.', () => {
    const inputOverLength5 = '123456';
    expect(isWithinMaxLength(inputOverLength5, maxLength)).toBe(false);
  });
});
