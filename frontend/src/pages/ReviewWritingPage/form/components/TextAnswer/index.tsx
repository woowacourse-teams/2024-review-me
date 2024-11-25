import { useFocusMessage, useTextAnswer } from '@/pages/ReviewWritingPage/form/hooks';
import { ReviewWritingCardQuestion } from '@/types';

import * as S from './styles';

interface TextAnswerProps {
  question: ReviewWritingCardQuestion;
}

const TextAnswer = ({ question }: TextAnswerProps) => {
  const { text, maxLength, errorMessage, handleTextAnswerBlur, handleTextAnswerChange } = useTextAnswer({
    question,
  });

  const { messageRef } = useFocusMessage<HTMLParagraphElement>({ isMessageShown: errorMessage !== '' });

  const textLength = `${text.length} / ${maxLength}`;

  return (
    <S.TextareaContainer>
      <S.Textarea
        data-testid={`${question.questionId}-textArea`}
        value={text}
        $isError={errorMessage !== ''}
        onChange={handleTextAnswerChange}
        onBlur={handleTextAnswerBlur}
      />
      <S.TextareaInfoContainer>
        <S.ReviewTextareaError tabIndex={-1} ref={messageRef} aria-live="assertive">
          {errorMessage}
        </S.ReviewTextareaError>
        <S.ReviewTextLength>{textLength}</S.ReviewTextLength>
      </S.TextareaInfoContainer>
    </S.TextareaContainer>
  );
};

export default TextAnswer;
