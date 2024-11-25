import { CheckboxItem, MultilineTextViewer } from '@/components';
import ContentModal from '@/components/common/modals/ContentModal';
import { ReviewWritingCardLayout, QnABoxLayout } from '@/pages/ReviewWritingPage/layout/components';
import { ReviewWritingAnswer, ReviewWritingCardSection } from '@/types';

import * as S from './styles';

export interface QuestionCardContainerStyleProps {
  $index: number;
}

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

  const generateSummary = () => {
    let summary = '작성한 리뷰 내용입니다.';

    questionSectionList.forEach((section) => {
      section.questions.forEach((question) => {
        if (question.questionType === 'CHECKBOX') {
          const selectedOptions = question.optionGroup?.options
            .filter((option) => isSelectedChoice(question.questionId, option.optionId))
            .map((option) => option.content)
            .join(', ');
          if (selectedOptions) summary += `질문: ${question.content}. 답변: ${selectedOptions}.`;
        } else if (question.questionType === 'TEXT') {
          const textAnswer = findTextAnswer(question.questionId);
          summary += `질문: ${question.content}. 답변: ${textAnswer ? textAnswer : '답변이 없습니다.'}.`;
        }
      });
    });

    return summary;
  };

  return (
    <ContentModal handleClose={closeModal}>
      <span className="sr-only">{generateSummary()}</span>
      <S.AnswerListModalContent aria-hidden={true} tabIndex={-1}>
        <S.AnswerListContainer>
          {questionSectionList.map((section) => (
            <ReviewWritingCardLayout cardSection={section} key={section.sectionId}>
              {section.questions.map((question) => (
                <QnABoxLayout question={question} isNeedGuideLine={false} key={question.questionId}>
                  {question.questionType === 'CHECKBOX' && (
                    <>
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
                    </>
                  )}

                  {question.questionType === 'TEXT' && (
                    <>
                      {findTextAnswer(question.questionId) ? (
                        <MultilineTextViewer text={findTextAnswer(question.questionId) as string} />
                      ) : (
                        <S.EmptyTextAnswer>작성한 답변이 없어요</S.EmptyTextAnswer>
                      )}
                    </>
                  )}
                </QnABoxLayout>
              ))}
            </ReviewWritingCardLayout>
          ))}
        </S.AnswerListContainer>
      </S.AnswerListModalContent>
    </ContentModal>
  );
};

export default AnswerListRecheckModal;
