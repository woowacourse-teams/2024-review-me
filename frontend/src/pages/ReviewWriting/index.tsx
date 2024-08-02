import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';

import { getDataToWriteReviewApi, postReviewApi } from '@/apis/review';
// import ClockLogo from '@/assets/clock.svg';
import GithubLogo from '@/assets/githubLogo.svg';
import { ConfirmModal, ErrorAlertModal } from '@/components';
import Button from '@/components/common/Button';
import { REVIEW } from '@/constants/review';
import { Keyword, ReviewContent, ReviewData, WritingReviewInfoData } from '@/types';

import LoadingPage from '../LoadingPage';

import KeywordButton from './components/KeywordButton';
// import RevieweeComment from './components/RevieweeComment';
import ReviewItem from './components/ReviewItem';
import * as S from './styles';

const SUBMIT_CONFIRM_MESSAGE = `리뷰를 제출할까요?
제출한 뒤에는 수정할 수 없어요.`;

const ReviewWritingPage = () => {
  const navigate = useNavigate();

  const [dataToWrite, setDataToWrite] = useState<WritingReviewInfoData | null>(null);
  const [answers, setAnswers] = useState<ReviewContent[]>([]);
  const [selectedKeywords, setSelectedKeywords] = useState<number[]>([]);

  const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);

  const isValidAnswersLength = !answers.some((id) => id.answer.length < REVIEW.answerMinLength);
  const isValidKeywordSelection =
    selectedKeywords.length >= REVIEW.keywordMinCount && selectedKeywords.length <= REVIEW.keywordMaxCount;
  const isValidForm = isValidAnswersLength && isValidKeywordSelection;

  const location = useLocation();
  const params = location.pathname.split('/');
  const reviewRequestCode = params.slice(-1).toString();

  useEffect(() => {
    const getDataToWrite = async () => {
      const data = await getDataToWriteReviewApi(reviewRequestCode);
      setDataToWrite(data);
      setAnswers(data.questions.map((question) => ({ questionId: question.id, answer: '' })));
    };

    getDataToWrite();
  }, []);

  const handleAnswerChange = (questionId: number, value: string) => {
    setAnswers((prev) =>
      prev.map((answer) => (answer.questionId === questionId ? { ...answer, answer: value } : answer)),
    );
  };

  const handleKeywordButtonClick = (keyword: Keyword) => {
    if (selectedKeywords.length === REVIEW.keywordMaxCount && !selectedKeywords.includes(keyword.id)) {
      alert('키워드는 최대 5개까지 선택할 수 있어요.');
      return;
    }

    setSelectedKeywords((prev) =>
      prev.includes(keyword.id) ? selectedKeywords.filter((id) => id !== keyword.id) : [...prev, keyword.id],
    );
  };

  const handleClickSubmitButton = async (event: React.FormEvent) => {
    event.preventDefault();

    setIsConfirmModalOpen(true);
  };

  const handleSubmitReview = async () => {
    const reviewData: ReviewData = {
      reviewRequestCode,
      reviewContents: answers,
      keywords: selectedKeywords,
    };

    try {
      await postReviewApi({ reviewData });
      setIsConfirmModalOpen(false);
      navigate('/user/review-writing-complete', { replace: true });
    } catch (error) {
      setIsConfirmModalOpen(false);
      setIsErrorModalOpen(true);
    }
  };

  if (!dataToWrite) return <LoadingPage />;

  return (
    <S.ReviewWritingPage onSubmit={handleClickSubmitButton}>
      <S.ReviewFormHeader>
        <S.ReviewInfoContainer>
          <S.LogoImage src={GithubLogo} alt="깃허브 로고" />
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
          cancelButton={{ type: 'secondary', text: '취소', handleClick: () => setIsConfirmModalOpen(false) }}
          handleClose={() => setIsConfirmModalOpen(false)}
          isClosableOnBackground={true}
        >
          {SUBMIT_CONFIRM_MESSAGE}
        </ConfirmModal>
      )}
      {isErrorModalOpen && (
        <ErrorAlertModal
          errorText="오류로 인해 리뷰를 제출할 수 없어요."
          closeButton={{ content: '닫기', type: 'primary', handleClick: () => setIsErrorModalOpen(false) }}
          handleClose={() => setIsErrorModalOpen(false)}
        />
      )}
    </S.ReviewWritingPage>
  );
};

export default ReviewWritingPage;
