import React from 'react';
import { Answer } from './styles';

interface ReviewAnswerProps {
  answer: string;
}

// NOTE: 개행 문자 처리하는 함수
const applyNewLine = (text: string): React.ReactNode => {
  return text.split('\n').map((line, index) => (
    <React.Fragment key={index}>
      {line}
      <br />
    </React.Fragment>
  ));
};

const ReviewAnswer = ({ answer }: ReviewAnswerProps) => {
  return <Answer>{applyNewLine(answer)}</Answer>;
};

export default ReviewAnswer;
