import { EssentialPropsWithChildren, ReviewWritingCardQuestion } from '@/types';

import AnswerGuide from '../AnswerGuide';

import * as S from './style';

interface QnABoxLayoutProps {
  question: ReviewWritingCardQuestion;
  isNeedGuideLine?: boolean;
}

const QnABoxLayout = ({
  question,
  isNeedGuideLine = true,
  children,
}: EssentialPropsWithChildren<QnABoxLayoutProps>) => {
  return (
    <S.QnASection>
      <S.QuestionTitle>
        {question.content}
        {isNeedGuideLine && <AnswerGuide question={question} />}
      </S.QuestionTitle>
      {question.guideline && <S.QuestionGuideline>{question.guideline}</S.QuestionGuideline>}
      {children}
    </S.QnASection>
  );
};

export default QnABoxLayout;
