import { MultilineTextViewer } from '@/components';

import ReviewSectionHeader from '../ReviewSectionHeader';

import * as S from './styles';

interface ReviewSectionProps {
  question: string;
  answer: string;
}

const ReviewSection = ({ question, answer }: ReviewSectionProps) => {
  return (
    <S.ReviewSection>
      <ReviewSectionHeader text={question} />
      {answer && (
        <S.Answer>
          <MultilineTextViewer text={answer} />
        </S.Answer>
      )}
    </S.ReviewSection>
  );
};

export default ReviewSection;
