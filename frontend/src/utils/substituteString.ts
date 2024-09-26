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
    // 조사 선택형이 아닌 경우 문자열만 치환
    if (!particleWithFinalConsonant || !particleWithoutFinalConsonant) return value;
    // 영어 이름인 경우 가능한 조사를 모두 포함해서 치환
    if (hasAlphabet.test(value)) {
      return value + `${particleWithFinalConsonant}(${particleWithoutFinalConsonant})`;
    }
    // 한글 이름인 경우 받침 유무에 따라 맞는 조사로 치환
    if (value) {
      return value + (hasFinalConsonant(value) ? particleWithFinalConsonant : particleWithoutFinalConsonant);
    }

    return value;
  });
};

export default substituteString;
