import { Options } from '@/types';

import MultipleChoiceAnswer from '../MultipleChoiceAnswer';

import * as S from './styles';

interface MultipleChoiceAnswerListProps {
  answerList: Options[];
}

const MultipleChoiceAnswerList = ({ answerList }: MultipleChoiceAnswerListProps) => {
  return (
    <S.MultipleChoiceAnswerList>
      {answerList.map(({ optionId, content }) => (
        <MultipleChoiceAnswer key={optionId} answer={content} />
      ))}
    </S.MultipleChoiceAnswerList>
  );
};

export default MultipleChoiceAnswerList;
