import { MultilineTextViewer } from '@/components';

import ReviewSectionHeader from '../ReviewSectionHeader';

import * as S from './styles';
interface ReviewSectionProps {
  question: string;
  answer: string;
  index: number;
}

const ReviewSection = ({ question, answer, index }: ReviewSectionProps) => {
  return (
    <S.ReviewSection>
      <ReviewSectionHeader number={index + 1} text={question} />
      <S.Answer>
        <MultilineTextViewer text={answer} />
      </S.Answer>
    </S.ReviewSection>
  );
};

export default ReviewSection;
