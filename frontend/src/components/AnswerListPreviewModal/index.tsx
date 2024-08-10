import { Fragment } from 'react';

import CheckboxItem from '../common/CheckboxItem';
import ContentModal from '../common/modals/ContentModal';

import { Section } from './answerList';
import QuestionCard from './components/QuestionCard';
import ReviewWritingCard from './components/ReviewWritingCard';
import * as S from './styles';

interface AnswerListPreviewModalProps {
  answerList: Section[];
  closeModal: () => void;
}

const AnswerListPreviewModal = ({ answerList, closeModal }: AnswerListPreviewModalProps) => {
  return (
    <ContentModal handleClose={closeModal}>
      <S.AnswerListContainer>
        <S.CardLayout>
          {answerList.map((section) => (
            <ReviewWritingCard key={section.sectionId} title={section.header}>
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
                            isChecked={option.isChecked}
                            isDisabled={true}
                            label={option.content}
                            $isReadonly={true}
                          />
                        ))}
                      </div>
                    )}
                    <div>{question.questionType === 'TEXT' && <div>{question.answer ?? ''}</div>}</div>
                  </S.ContentContainer>
                </Fragment>
              ))}
            </ReviewWritingCard>
          ))}
        </S.CardLayout>
      </S.AnswerListContainer>
    </ContentModal>
  );
};

export default AnswerListPreviewModal;
