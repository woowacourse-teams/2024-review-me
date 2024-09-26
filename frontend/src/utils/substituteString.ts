import hasFinalConsonant from './hasFinalConsonant';

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
