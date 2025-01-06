import { Options } from '@/types';

import MultipleChoiceAnswer from '../MultipleChoiceAnswer';

import * as S from './styles';

interface MultipleChoiceAnswerListProps {
  selectedOptionList: Options[];
}

const MultipleChoiceAnswerList = ({ selectedOptionList }: MultipleChoiceAnswerListProps) => {
  return (
    <S.MultipleChoiceAnswerList>
      {selectedOptionList.map(({ optionId, content }) => (
        <MultipleChoiceAnswer key={optionId} selectedOption={content} />
      ))}
    </S.MultipleChoiceAnswerList>
  );
};

export default MultipleChoiceAnswerList;
