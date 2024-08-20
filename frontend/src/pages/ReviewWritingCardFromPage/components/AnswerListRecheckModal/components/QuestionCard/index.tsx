import { QuestionCardStyleType } from '@/types';

import * as S from './styles';

interface QuestionCardProps {
  questionType: QuestionCardStyleType;
  question: string;
}

const QuestionCard = ({ questionType, question }: QuestionCardProps) => {
  return <S.QuestionCard questionType={questionType}>{question}</S.QuestionCard>;
};

export default QuestionCard;
