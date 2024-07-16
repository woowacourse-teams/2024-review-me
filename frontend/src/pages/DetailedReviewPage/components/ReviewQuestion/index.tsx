import * as S from './styles';

interface ReviewQuestionProps {
  question: string;
}

const ReviewQuestion = ({ question }: ReviewQuestionProps) => {
  return <S.Question>{question}</S.Question>;
};

export default ReviewQuestion;
