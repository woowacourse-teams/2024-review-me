import hasFinalConsonant from './hasFinalConsonant';

interface SubstituteStringProps {
  content: string;
  variables: { [key: string]: string };
}

/**
 * 문자열을 치환하는 함수
 * 영어를 포함할 경우 두 조사를 모두 반환
 * ${content/받침없는조사:받침있는조사}의 형식일 경우 조사 계산 후 치환
 * ${content}의 형식일 경우 단순 치환
 */
const substituteString = ({ content, variables }: SubstituteStringProps) => {
  const regex = /\$\{(\w+)(?:\/([^:]+):([^}]+))?\}/g;
  const hasAlphabet = /[a-zA-Z]/;

  return content.replace(regex, (_, variableName, particleWithoutFinalConsonant, particleWithFinalConsonant) => {
    const value = variables[variableName];

    if (hasAlphabet.test(value)) {
      return value + `${particleWithFinalConsonant}(${particleWithoutFinalConsonant})`;
    }

    if (value && particleWithoutFinalConsonant && particleWithFinalConsonant) {
      return value + (hasFinalConsonant(value) ? particleWithFinalConsonant : particleWithoutFinalConsonant);
    }
    return value;
  });
};

export default substituteString;
