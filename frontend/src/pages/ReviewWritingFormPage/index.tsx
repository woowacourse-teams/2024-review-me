import { useEffect, useRef, useState } from 'react';

import { Button } from '@/components';
import QuestionCard from '@/components/QuestionCard';
import ReviewWritingCard from '@/components/ReviewWritingCard';
import { ButtonStyleType } from '@/types';

import * as S from './styles';

const REVIEWEE = '쑤쑤';

const QUESTIONS = [
  {
    title: `💡${REVIEWEE}와 함께 한 기억을 떠올려볼게요.`,
    question: `프로젝트 기간 동안, ${REVIEWEE}의 강점이 드러났던 순간을 선택해주세요. (1~2개)`,
  },
  {
    title: `선택한 순간들을 바탕으로 ${REVIEWEE}에 대한 리뷰를 작성해볼게요.`,
    question: `커뮤니케이션, 협업 능력에서 어떤 부분이 인상 깊었는지 선택해주세요. (1개 이상)`,
  },
  {
    title: ``,
    question: `앞으로의 성장을 위해서 ${REVIEWEE}가 어떤 목표를 설정하면 좋을까요?`,
  },
];

const ReviewWritingFormPage = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [slideWidth, setSlideWidth] = useState(0);

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
    if (currentIndex < QUESTIONS.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const buttons = [
    { styleType: 'secondary' as ButtonStyleType, onClick: handlePrev, text: '이전' },
    { styleType: 'primary' as ButtonStyleType, onClick: handleNext, text: '다음' },
  ];

  return (
    <S.CardLayout>
      <S.SliderContainer ref={wrapperRef} translateX={currentIndex * slideWidth}>
        {QUESTIONS.map(({ question, title }, index) => (
          <S.Slide key={index}>
            <ReviewWritingCard title={title}>
              <QuestionCard questionType="normal" question={question} />
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
