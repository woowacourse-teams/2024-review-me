import formatKeyword from '@/utils/formatKeyword';

const MultipleChoiceAnswer = ({ answer }: { answer: string }) => {
  const formattedAnswer = formatKeyword(answer);

  return <li>{formattedAnswer}</li>;
};

export default MultipleChoiceAnswer;
