import hasFinalConsonant from './hasFinalConsonant';

interface CalculateParticleProps {
  target: string;
  particles: {
    withFinalConsonant: string;
    withoutFinalConsonant: string;
  };
}

// target에 맞는 조사를 반환하는 함수
const calculateParticle = ({ target, particles }: CalculateParticleProps) => {
  const hasAlphabet = /[a-zA-Z]/;

  if (hasAlphabet.test(target)) return `${particles.withFinalConsonant}(${particles.withoutFinalConsonant})`;

  if (hasFinalConsonant(target)) return particles.withFinalConsonant;
  return particles.withoutFinalConsonant;
};

export default calculateParticle;
