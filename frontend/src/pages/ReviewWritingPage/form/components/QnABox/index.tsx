import { MultipleChoiceAnswer, TextAnswer } from '@/pages/ReviewWritingPage/form/components';
import { QnABoxLayout } from '@/pages/ReviewWritingPage/layout';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './style';

interface QnABoxProps {
  question: ReviewWritingCardQuestion;
}
/**
 * 하나의 질문과 그에 대한 답을 관리
 */
const QnABox = ({ question }: QnABoxProps) => {
  return (
    <QnABoxLayout question={question}>
      {question.guideline && <S.QuestionGuideline>{question.guideline}</S.QuestionGuideline>}
      {question.questionType === 'CHECKBOX' && <MultipleChoiceAnswer question={question} />}
      {question.questionType === 'TEXT' && <TextAnswer question={question} />}
    </QnABoxLayout>
  );
};

export default QnABox;
