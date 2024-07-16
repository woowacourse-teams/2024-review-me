import ReviewQuestion from '../ReviewQuestion';
import ReviewAnswer from '../ReviewAnswer';
import * as S from './styles';
interface ReviewSectionProps {
  question: string;
  answer: string;
}

const ReviewViewSection = ({ question, answer }: ReviewSectionProps) => {
  return (
    <S.ReviewSectionContainer>
      <ReviewQuestion question={question}></ReviewQuestion>
      <ReviewAnswer answer={answer}></ReviewAnswer>
    </S.ReviewSectionContainer>
  );
};

export default ReviewViewSection;
