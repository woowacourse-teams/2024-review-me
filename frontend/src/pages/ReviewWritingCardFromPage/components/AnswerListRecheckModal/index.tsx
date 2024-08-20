import { Fragment } from 'react';

import { CheckboxItem, MultilineTextViewer } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';
import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import QuestionCard from './components/QuestionCard';
import ReviewCard from './components/ReviewCard';
import * as S from './styles';

interface AnswerListRecheckModalProps {
  questionSectionList: ReviewWritingCardSection[];
  answerMap: Map<number, ReviewWritingAnswer>;
  closeModal: () => void;
}

const AnswerListRecheckModal = ({ questionSectionList, answerMap, closeModal }: AnswerListRecheckModalProps) => {
  const isSelectedChoice = (questionId: number, optionId: number) => {
    const answer = answerMap.get(questionId);
    if (!answer) return false;

    return !!answer.selectedOptionIds?.some((id) => id === optionId);
  };

  const findTextAnswer = (questionId: number) => {
    const answer = answerMap.get(questionId);
    return answer ? answer.text : '';
  };

  return (
    <ContentModal handleClose={closeModal}>
      <S.AnswerListContainer>
        <S.CardLayout>
          {questionSectionList.map((section) => (
            <S.ReviewCardWrapper key={section.sectionId}>
              <ReviewCard title={section.header}>
                {section.questions.map((question) => (
                  <Fragment key={question.questionId}>
                    <QuestionCard questionType="normal" question={question.content} />
                    <S.ContentContainer>
                      {question.questionType === 'CHECKBOX' && (
                        <div>
                          {question.optionGroup?.options.map((option, index) => (
                            <CheckboxItem
                              key={`${question.questionId}_${index}`}
                              id={`${question.questionId}_${index}`}
                              name={`${question.questionId}_${index}`}
                              isChecked={isSelectedChoice(question.questionId, option.optionId)}
                              isDisabled={true}
                              label={option.content}
                              $isReadonly={true}
                            />
                          ))}
                        </div>
                      )}
                        </S.TextAnswerWrapper>
                      )}
                    </S.ContentContainer>
                  </Fragment>
                ))}
              </ReviewCard>
            </S.ReviewCardWrapper>
          ))}
        </S.CardLayout>
      </S.AnswerListContainer>
    </ContentModal>
  );
};

export default AnswerListRecheckModal;