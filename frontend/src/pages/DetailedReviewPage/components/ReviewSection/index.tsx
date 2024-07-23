import { MultilineTextViewer } from '@/components';

import * as S from './styles';
interface ReviewSectionProps {
  question: string;
  answer: string;
}

const ReviewSection = ({ question, answer }: ReviewSectionProps) => {
  return (
    <S.ReviewSection>
      <S.Question>{question}</S.Question>
      <S.Answer>
        <MultilineTextViewer text={answer} />
      </S.Answer>
    </S.ReviewSection>
  );
};

export default ReviewSection;
