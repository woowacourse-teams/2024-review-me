import ReviewQuestion from '../ReviewQuestion';
import ReviewAnswer from '../ReviewAnswer';

interface ReviewSectionProps {
  question: string;
  answer: string;
}

const ReviewViewSection = ({ question, answer }: ReviewSectionProps) => {
  return (
    <section>
      <ReviewQuestion question={question}></ReviewQuestion>
      <ReviewAnswer answer={answer}></ReviewAnswer>
    </section>
  );
};

export default ReviewViewSection;
