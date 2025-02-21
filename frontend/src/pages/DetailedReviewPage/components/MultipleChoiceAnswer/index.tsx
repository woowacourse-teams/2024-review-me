import formatKeyword from '@/utils/formatKeyword';

const MultipleChoiceAnswer = ({ selectedOption }: { selectedOption: string }) => {
  const isExampleIncluded = selectedOption.includes('예');

  const formattedAnswer = formatKeyword(selectedOption);

  return <li>{isExampleIncluded ? formattedAnswer : selectedOption}</li>;
};

export default MultipleChoiceAnswer;
