import { EssentialPropsWithChildren, ReviewWritingCardQuestion } from '@/types';

import QuestionGuide from '../QuestionGuide';

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
        {isNeedGuideLine && <QuestionGuide question={question} />}
      </S.QuestionTitle>
      {children}
    </S.QnASection>
  );
};

export default QnABoxLayout;
