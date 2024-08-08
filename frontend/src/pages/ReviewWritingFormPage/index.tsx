import { useEffect, useRef, useState } from 'react';

import { Button } from '@/components';
import CheckboxItem from '@/components/common/CheckboxItem';
import LongReviewItem from '@/components/common/LongReviewItem';
import { ButtonStyleType } from '@/types';

import { QuestionCard, ReviewWritingCard } from './components';
import { AnswerType, COMMON_QUESTIONS, ESSAY, QuestionType, TAIL_QUESTIONS, TailQuestionType } from './question';
import * as S from './styles';

const ReviewWritingFormPage = () => {
  const [questions, setQuestions] = useState<(QuestionType | TailQuestionType)[]>([COMMON_QUESTIONS[0]]);
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
    // 1. 카테고리 저장하기
    if (currentIndex === 0 && answers) {
      const selectedCategoryList = answers[0].choiceAnswer;
      if (!selectedCategoryList) return;

      const selectedCategoryQuestionList = TAIL_QUESTIONS.filter((question) =>
        selectedCategoryList.includes(question.tailCategory),
      );
      const newQuestions = [COMMON_QUESTIONS[0], ...selectedCategoryQuestionList, ...COMMON_QUESTIONS.slice(1)];
      setQuestions(newQuestions);
    }

    // 2. 페이지 이동
    if (currentIndex < questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const handleSubmit = () => {
    console.log('---제출되는 답변---');
    console.log('답번:', answers);
  };

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

  const isValidatedAnswer = (index: number) => {
    const currentQuestion = questions[index];
    if (!currentQuestion) return;

    const { targetAnswer } = findTargetAnswer(currentQuestion.name);
    const { answerType, isExtraEssay } = currentQuestion;
    if (!targetAnswer) return false;
    const { choiceAnswer, essayAnswer } = targetAnswer;
    // case1. 객관식만
    if (answerType === 'choice' && !isExtraEssay) return !!choiceAnswer?.length;
    // case2. 객관식 + 서술형
    if (answerType === 'choice' && isExtraEssay) return !!choiceAnswer?.length && !!essayAnswer;

    // case3. 서술형
    if (answerType === 'essay') return !!essayAnswer;
  };

  const isAbleSubmit = () => {
    return !!answers?.every((answer, index) => isValidatedAnswer(index));
  };

  const isShowSubmitButton = () => {
    return currentIndex && currentIndex === questions.length - 1;
  };
  const buttons = [
    {
      styleType: currentIndex === 0 ? 'disabled' : ('secondary' as ButtonStyleType),
      onClick: handlePrev,
      text: '이전',
      disabled: currentIndex === 0,
    },
    {
      styleType: isAbleSubmit() || isValidatedAnswer(currentIndex) ? ('primary' as ButtonStyleType) : 'disabled',
      onClick: isShowSubmitButton() ? handleSubmit : handleNext,
      text: isShowSubmitButton() ? '제출' : '다음',
      disabled: isShowSubmitButton() ? isAbleSubmit() : isValidatedAnswer(currentIndex),
    },
  ];

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
    if (answers && !targetAnswer) {
      newAnswers = [...answers, { questionName, choiceAnswer: newChoice }];
    }
    // 2-3. answer가 없는 경우
    if (!answers) {
      newAnswers = [{ questionName, choiceAnswer: newChoice }];
    }
    if (newAnswers) {
      setAnswers(newAnswers);
    }
  };

  const isValidatedEssayLength = (value: string) => {
    const MIN = 20;
    const MAX = 1000;

    return value.length >= MIN && value.length <= MAX;
  };
  interface HandleEssayChangeParams {
    event: React.ChangeEvent<HTMLTextAreaElement>;
    questionName: string;
  }
  const handleEssayChange = ({ event, questionName }: HandleEssayChangeParams) => {
    const essayAnswer = isValidatedEssayLength(event.target.value) ? event.target.value : undefined;
    // 서술형 답볍을 새로 하는 상황 - 아예 answer가 없는 경우
    if (!answers) {
      return setAnswers([
        {
          questionName,
          essayAnswer,
        },
      ]);
    }
    const newAnswers = [...answers];
    const { targetAnswer, targetAnswerIndex } = findTargetAnswer(questionName);
    //서술형 답볍을 바꾸는 상황
    if (targetAnswer && targetAnswerIndex && answers) {
      const newTargetAnswer: AnswerType = {
        ...targetAnswer,
        essayAnswer,
      };
      newAnswers.splice(targetAnswerIndex, 1, newTargetAnswer);
      setAnswers(newAnswers);
    }
    // 해당 답변에 대한 새로운 서술형 답변을 넣는 경우
    if (answers) {
      const newTargetAnswer: AnswerType = {
        questionName,
        essayAnswer,
      };
      setAnswers(newAnswers.concat(newTargetAnswer));
    }
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

              {question.isExtraEssay && (
                <>
                  <QuestionCard questionType="normal" question={question.question} />
                  <QuestionCard
                    questionType="guideline"
                    question={ESSAY.find((essay) => essay.name === question.name)?.guideLine ?? ''}
                  />

                  <LongReviewItem
                    initialValue={findTargetAnswer(question.name).targetAnswer?.essayAnswer}
                    minLength={20}
                    maxLength={1000}
                    handleTextareaChange={(event) => handleEssayChange({ event, questionName: question.name })}
                  />
                </>
              )}
              {question.answerType === 'essay' && (
                <LongReviewItem
                  initialValue={findTargetAnswer(question.name).targetAnswer?.essayAnswer}
                  minLength={20}
                  maxLength={1000}
                  handleTextareaChange={(event) => handleEssayChange({ event, questionName: question.name })}
                />
              )}
            </ReviewWritingCard>
          </S.Slide>
        ))}
      </S.SliderContainer>
      <S.ButtonContainer>
        {buttons.map((button, index) => (
          <Button key={index} styleType={button.styleType} onClick={button.onClick} disabled={button.disabled}>
            {button.text}
          </Button>
        ))}
      </S.ButtonContainer>
    </S.CardLayout>
  );
};

export default ReviewWritingFormPage;
