import * as S from './styles';

interface QuestionProps {
  text: string;
}

const QuestionTitle = ({ text }: QuestionProps) => {
  return <S.QuestionTitle>{text}</S.QuestionTitle>;
};

export default QuestionTitle;
