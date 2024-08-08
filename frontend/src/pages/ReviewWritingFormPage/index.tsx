import { useEffect, useRef, useState } from 'react';

import { Button } from '@/components';
import CheckboxItem from '@/components/common/CheckboxItem';
import { ButtonStyleType } from '@/types';

import { QuestionCard, ReviewWritingCard } from './components';
import { AnswerType, COMMON_QUESTIONS, QuestionType, TAIL_QUESTIONS } from './question';
import * as S from './styles';

const ReviewWritingFormPage = () => {
  const [questions, setQuestions] = useState<QuestionType[]>(COMMON_QUESTIONS);
  const [answers, setAnswers] = useState<AnswerType[] | null>(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [slideWidth, setSlideWidth] = useState(0);
  const [isOpenLimitGuide, setIsOpenLimitGuide] = useState(false);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (wrapperRef.current) setSlideWidth(wrapperRef.current.clientWidth);
  }, [wrapperRef]);

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  const handleNext = () => {
    if (currentIndex < COMMON_QUESTIONS.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const buttons = [
    { styleType: 'secondary' as ButtonStyleType, onClick: handlePrev, text: '이전' },
    { styleType: 'primary' as ButtonStyleType, onClick: handleNext, text: '다음' },
    // NOTE: 제출 버튼은 따로 만들어야 하남
  ];

  const findTargetAnswer = (questionName: string) => {
    const targetAnswer = answers?.find((answer) => answer.questionName === questionName);
    const targetAnswerIndex = answers?.findIndex((answer) => answer.questionName === questionName);

    return { targetAnswer, targetAnswerIndex };
  };
  const findTargetQuestion = (questionName: string) => {
    return {
      targetQuestion: questions.find((question) => question.name === questionName),
      targetQuestionIndex: questions.findIndex((question) => question.name === questionName),
    };
  };

  const findTargetQuestionChoiceLimit = (questionName: string) => {
    const { targetQuestion } = findTargetQuestion(questionName);
    return { minLength: targetQuestion?.choiceMinLength, maxLength: targetQuestion?.choiceMaxLength };
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>, label: string) => {
    const { name, checked } = event.currentTarget;
    const questionName = name.split('_')[0];
    const { targetAnswer, targetAnswerIndex } = findTargetAnswer(questionName);
    const { maxLength } = findTargetQuestionChoiceLimit(questionName);

    //최대 개수 도달 시 안내 문구 띄우기
    if (targetAnswer?.choiceAnswer && targetAnswer.choiceAnswer.every((choice) => choice !== label) && maxLength) {
      const isMaxLength = targetAnswer.choiceAnswer.length >= maxLength;
      if (isMaxLength) return setIsOpenLimitGuide(true);
    }
    setIsOpenLimitGuide(false);

    let newChoice = targetAnswer?.choiceAnswer;
    let newAnswers;

    // 1. 새로운 선택 답변 만들기
    // 문항을 추가하려는 지
    if (checked) {
      newChoice = newChoice ? newChoice.concat(label) : [label];
    }
    // 문항을 취소하려는 지
    if (!checked) {
      newChoice = newChoice?.filter((choice) => choice !== label);
    }
    // 2. 새로운 선택 답변을 넣은 새로운 answers
    // 2-1 answer가 있고, targetAnswer가 있는 경우
    if (targetAnswerIndex !== undefined && answers && targetAnswer) {
      newAnswers = [...answers];
      newAnswers.splice(targetAnswerIndex, 1, { ...targetAnswer, choiceAnswer: newChoice });
    }
    // 2-2. answer가 있고, targetAnswer가 없는 경우
    if (answers && !targetAnswer && !targetAnswerIndex) {
      newAnswers = [...answers, { questionName, choiceAnswer: newChoice }];
    }
    // 2-3. answer가 없는 경우
    if (!answers) {
      newAnswers = [{ questionName, choiceAnswer: newChoice }];
    }
    setAnswers(newAnswers ?? null);
  };

  const isSelectedCheckbox = (questionName: string, option: string) => {
    const targetAnswer = answers?.find((answer) => answer.questionName === questionName);
    if (!targetAnswer?.choiceAnswer) return false;

    return targetAnswer.choiceAnswer.some((choice) => choice === option);
  };

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} translateX={currentIndex * slideWidth}>
        {questions.map((question, index) => (
          <S.Slide key={index}>
            <ReviewWritingCard title={question.title}>
              <QuestionCard questionType="normal" question={question.question} />

              {question.answerType === 'choice' &&
                question.options?.map((option, index) => (
                  <CheckboxItem
                    key={`${question.name}_${index}`}
                    id={`${question.name}_${index}`}
                    name={`${question.name}_${index}`}
                    isChecked={isSelectedCheckbox(question.name, option)}
                    isDisabled={false}
                    label={option}
                    onChange={(event) => handleCheckboxChange(event, option)}
                  />
                ))}
              {question.answerType === 'choice' && isOpenLimitGuide && (
                <S.LimitGuideMessage>
                  😮 최대 {findTargetQuestionChoiceLimit(question.name).maxLength}개까지 선택가능해요.
                </S.LimitGuideMessage>
              )}
              {/* {question.answerType ==='essay' &&}
              {question.isExtraEssay && // 서술형 TAIL_QUESTIONS.find((value)=> value.name === question.name)} */}
            </ReviewWritingCard>
          </S.Slide>
        ))}
      </S.SliderContainer>
      <S.ButtonContainer>
        {buttons.map((button, index) => (
          <Button key={index} styleType={button.styleType} onClick={button.onClick}>
            {button.text}
          </Button>
        ))}
      </S.ButtonContainer>
    </S.CardLayout>
  );
};

export default ReviewWritingFormPage;
