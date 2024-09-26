const hasFinalConsonant = (word: string) => {
  const code = word.charCodeAt(word.length - 1);
  return (code - 0xac00) % 28 !== 0;
};

interface SubstituteStringProps {
  content: string;
  variables: { [key: string]: string };
}

const substituteString = ({ content, variables }: SubstituteStringProps) => {
  const regex = /\$\{(\w+)(?:\/([^:]+):([^}]+))?\}/g;

  return content.replace(regex, (_, variableName, particleWithoutFinalConsonant, particleWithFinalConsonant) => {
    const value = variables[variableName];

    if (value !== undefined && particleWithoutFinalConsonant && particleWithFinalConsonant) {
      return value + (hasFinalConsonant(value) ? particleWithFinalConsonant : particleWithoutFinalConsonant);
    }
    return value;
  });
};

export default substituteString;
