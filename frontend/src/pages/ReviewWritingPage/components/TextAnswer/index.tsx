import { ReviewWritingCardQuestion } from '@/types';

import useTextAnswer from './hooks/useTextAnswer';
import * as S from './styles';

interface TextAnswerProps {
  question: ReviewWritingCardQuestion;
}

const TextAnswer = ({ question }: TextAnswerProps) => {
  const { text, minLength, maxLength, errorMessage, handleTextAnswerBlur, handleTextAnswerChange } = useTextAnswer({
    question,
  });

  const textLength = `${text.length} / ${maxLength}`;

  return (
    <S.TextareaContainer>
      <S.Textarea
        value={text}
        $isError={errorMessage !== ''}
        onChange={handleTextAnswerChange}
        onBlur={handleTextAnswerBlur}
        placeholder={`최소 ${minLength}자 이상, 최대 ${maxLength}자까지 입력 가능해요`}
      />
      <S.TextareaInfoContainer>
        <S.ReviewTextareaError>{errorMessage}</S.ReviewTextareaError>
        <S.ReviewTextLength>{textLength}</S.ReviewTextLength>
      </S.TextareaInfoContainer>
    </S.TextareaContainer>
  );
};

export default TextAnswer;
