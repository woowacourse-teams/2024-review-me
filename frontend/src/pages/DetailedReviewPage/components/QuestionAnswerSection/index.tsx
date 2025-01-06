import { MultilineTextViewer } from '@/components';
import { Options, QuestionType } from '@/types';

import MultipleChoiceAnswerList from '../MultipleChoiceAnswerList';
import QuestionTitle from '../QuestionTitle';

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
      {questionType === 'TEXT' && answer && <MultilineTextViewer text={answer} />}
    </S.QuestionAnswerSection>
  );
};

export default QuestionAnswerSection;
