import { MultilineTextViewer } from '@/components';
import { MultipleChoiceAnswerList, QuestionTitle } from '@/pages/DetailedReviewPage/components';
import { Options, QuestionType } from '@/types';

import * as S from './styles';

interface QuestionAnswerProps {
  question: string;
  questionType: QuestionType;
  answer?: string;
  options?: Options[];
}

const QuestionAnswerSection = ({ question, questionType, answer, options }: QuestionAnswerProps) => {
  return (
    <S.QuestionAnswerSection>
      <QuestionTitle text={question} />
      {questionType === 'CHECKBOX' && options && <MultipleChoiceAnswerList selectedOptionList={options} />}
      {questionType === 'TEXT' && answer && (
        <S.TextAnswerWrapper>
          <MultilineTextViewer text={answer} />
        </S.TextAnswerWrapper>
      )}
    </S.QuestionAnswerSection>
  );
};

export default QuestionAnswerSection;
