import hasFinalConsonant from './hasFinalConsonant';

interface CalculateParticleProps {
  target: string;
  particles: {
    withFinalConsonant: string;
    withoutFinalConsonant: string;
  };
}

const calculateParticle = ({ target, particles }: CalculateParticleProps) => {
  if (hasFinalConsonant(target)) return particles.withFinalConsonant;
  return particles.withoutFinalConsonant;
};

export default calculateParticle;
