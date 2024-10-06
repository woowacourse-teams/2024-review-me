import * as S from './style';
interface SentenceProps {
  isHighlight: boolean;
  text: string;
}

const Sentence = ({ isHighlight, text }: SentenceProps) => {
  return <S.Sentence $isHighlight={isHighlight}>{text}</S.Sentence>;
};

export default Sentence;
