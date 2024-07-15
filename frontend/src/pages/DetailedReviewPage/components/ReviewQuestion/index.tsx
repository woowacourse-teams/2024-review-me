import { Question } from './styles';

interface ReviewQuestionProps {
  question: string;
}

const ReviewQuestion = ({ question }: ReviewQuestionProps) => {
  return <Question>{question}</Question>;
};

export default ReviewQuestion;
