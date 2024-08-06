import { useNavigate } from 'react-router';

import { postReviewApi } from '@/apis/review';
// import ClockLogo from '@/assets/clock.svg';
import GithubLogoIcon from '@/assets/githubLogo.svg';
import { ConfirmModal, ErrorAlertModal } from '@/components';
import Button from '@/components/common/Button';
import useConfirmModal from '@/hooks/useConfirmModal';
import useErrorModal from '@/hooks/useErrorModal';
import useReviewForm from '@/hooks/useReviewForm';
import useReviewRequestCode from '@/hooks/useReviewRequestCode';
import { ReviewData } from '@/types';

import LoadingPage from '../LoadingPage';

import KeywordButton from './components/KeywordButton';
// import RevieweeComment from './components/RevieweeComment';
import ReviewItem from './components/ReviewItem';
import * as S from './styles';

const SUBMIT_CONFIRM_MESSAGE = `리뷰를 제출할까요?
제출한 뒤에는 수정할 수 없어요.`;

const ReviewWritingPage = () => {
  const navigate = useNavigate();

  const { reviewRequestCode } = useReviewRequestCode();

  const { isConfirmModalOpen, openConfirmModal, closeConfirmModal } = useConfirmModal();
  const { isErrorModalOpen, errorMessage, openErrorModal, closeErrorModal } = useErrorModal();

  const { dataToWrite, answers, selectedKeywords, isValidForm, handleAnswerChange, handleKeywordButtonClick } =
    useReviewForm({ reviewRequestCode, openErrorModal });

  const handleClickSubmitButton = async (event: React.FormEvent) => {
    event.preventDefault();
    openConfirmModal();
  };

  const handleSubmitReview = async () => {
    const reviewData: ReviewData = {
      reviewRequestCode,
      reviewContents: answers,
      keywords: selectedKeywords,
    };

    try {
      await postReviewApi({ reviewData });
      closeConfirmModal();
      navigate('/user/review-writing-complete', { replace: true });
    } catch (error) {
      closeConfirmModal();
      openErrorModal('리뷰를 제출할 수 없어요.');
    }
  };

  if (!dataToWrite) return <LoadingPage />;

  return (
    <S.ReviewWritingPage onSubmit={handleClickSubmitButton}>
      <S.ReviewFormHeader>
        <S.ReviewInfoContainer>
          <S.LogoImage src={GithubLogoIcon} alt="깃허브 로고" />
          <S.Container>
            <S.ProjectName>{dataToWrite.projectName}</S.ProjectName>
            <S.ReviewInfo>
              <S.Reviewee>
                <span>{dataToWrite.revieweeName}</span>님을 리뷰해주세요!
              </S.Reviewee>
              {/* <S.ReviewExpirationDate>
                <S.ClockImage src={ClockLogo} alt="시계" />
                리뷰 마감일: {dataToWrite.reviewerGroup.deadline.toString()}
              </S.ReviewExpirationDate> */}
            </S.ReviewInfo>
          </S.Container>
        </S.ReviewInfoContainer>
        {/* <RevieweeComment text={dataToWrite.reviewerGroup.description}></RevieweeComment> */}
      </S.ReviewFormHeader>
      <S.ReviewFormMain>
        <S.ReviewContainer>
          {dataToWrite.questions.map((question, index) => {
            return (
              <ReviewItem
                question={`${index + 1}. ${question.content}`}
                key={question.id}
                answerValue={answers.find((answer) => answer.questionId === question.id)?.answer || ''}
                handleWrite={(value) => handleAnswerChange(question.id, value)}
              />
            );
          })}
        </S.ReviewContainer>
        <S.KeywordContainer>
          <S.KeywordTitle>{dataToWrite.questions.length + 1}. 키워드</S.KeywordTitle>
          <S.KeywordList>
            {dataToWrite.keywords.map((keyword) => {
              return (
                <KeywordButton
                  isSelected={selectedKeywords.includes(keyword.id)}
                  key={keyword.id}
                  onClick={() => handleKeywordButtonClick(keyword)}
                >
                  {keyword.content}
                </KeywordButton>
              );
            })}
          </S.KeywordList>
        </S.KeywordContainer>
        <S.ButtonContainer>
          {/* <Button buttonType="secondary" text="저장" /> */}
          <Button styleType={isValidForm ? 'primary' : 'disabled'} type="submit">
            제출
          </Button>
        </S.ButtonContainer>
      </S.ReviewFormMain>
      {isConfirmModalOpen && (
        <ConfirmModal
          confirmButton={{ type: 'primary', text: '확인', handleClick: handleSubmitReview }}
          cancelButton={{ type: 'secondary', text: '취소', handleClick: closeConfirmModal }}
          handleClose={closeConfirmModal}
          isClosableOnBackground={true}
        >
          {SUBMIT_CONFIRM_MESSAGE}
        </ConfirmModal>
      )}
      {isErrorModalOpen && (
        <ErrorAlertModal
          errorText={errorMessage}
          closeButton={{ content: '닫기', type: 'primary', handleClick: closeErrorModal }}
          handleClose={closeErrorModal}
        />
      )}
    </S.ReviewWritingPage>
  );
};

export default ReviewWritingPage;
