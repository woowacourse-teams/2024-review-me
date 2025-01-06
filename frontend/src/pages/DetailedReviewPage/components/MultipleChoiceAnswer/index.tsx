import formatKeyword from '@/utils/formatKeyword';

const MultipleChoiceAnswer = ({ selectedOption }: { selectedOption: string }) => {
  const formattedAnswer = formatKeyword(selectedOption);

  return <li>{formattedAnswer}</li>;
};

export default MultipleChoiceAnswer;
