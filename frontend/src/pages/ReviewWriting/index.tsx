import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { getDataToWriteReviewApi, postReviewApi } from '@/apis/review';
import ClockLogo from '@/assets/clock.svg';
import GithubLogo from '@/assets/githubLogo.svg';
import Button from '@/components/common/Button';
import { REVIEW } from '@/constants/review';
import { Keyword, ReviewContent, ReviewData, WritingReviewInfoData } from '@/types';

import LoadingPage from '../LoadingPage';

import KeywordButton from './components/KeywordButton';
import RevieweeComment from './components/RevieweeComment';
import ReviewItem from './components/ReviewItem';
import * as S from './styles';

const ReviewWritingPage = () => {
  const navigate = useNavigate();

  const [dataToWrite, setDataToWrite] = useState<WritingReviewInfoData | null>(null);
  const [answers, setAnswers] = useState<ReviewContent[]>([]);
  const [selectedKeywords, setSelectedKeywords] = useState<number[]>([]);

  const isValidAnswersLength = !answers.some((id) => id.answer.length < REVIEW.answerMinLength);
  const isValidKeywordSelection =
    selectedKeywords.length >= REVIEW.keywordMinCount && selectedKeywords.length <= REVIEW.keywordMaxCount;
  const isValidForm = isValidAnswersLength && isValidKeywordSelection;

  useEffect(() => {
    const getDataToWrite = async () => {
      const data = await getDataToWriteReviewApi(1);
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

  const handleSubmitReview = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!confirm('리뷰를 제출할까요? 제출한 뒤에는 수정할 수 없어요.')) return;

    if (!dataToWrite) return;

    const reviewData: ReviewData = {
      reviewerId: 10,
      reviewerGroupId: dataToWrite.reviewerGroup.id,
      reviewContents: answers,
      keywords: selectedKeywords,
    };

    try {
      await postReviewApi({ reviewData });
      navigate('/user/review-writing-complete', { replace: true });
    } catch (error) {
      console.error('Failed to submit review:', error);
    }
  };

  if (!dataToWrite) return <LoadingPage />;

  return (
    <S.ReviewWritingPage onSubmit={handleSubmitReview}>
      <S.ReviewFormHeader>
        <S.ReviewInfoContainer>
          <S.LogoImage src={GithubLogo} alt="깃허브 로고" />
          <S.Container>
            <S.ProjectName>{dataToWrite.reviewerGroup.name}</S.ProjectName>
            <S.ReviewInfo>
              <S.Reviewee>
                <span>{dataToWrite.reviewerGroup.reviewee.name}</span>님을 리뷰해주세요!
              </S.Reviewee>
              <S.ReviewExpirationDate>
                <S.ClockImage src={ClockLogo} alt="시계" />
                리뷰 마감일: {dataToWrite.reviewerGroup.deadline.toString()}
              </S.ReviewExpirationDate>
            </S.ReviewInfo>
          </S.Container>
        </S.ReviewInfoContainer>
        <RevieweeComment text={dataToWrite.reviewerGroup.description}></RevieweeComment>
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
          <Button buttonType={isValidForm ? 'primary' : 'disabled'} text="제출" />
        </S.ButtonContainer>
      </S.ReviewFormMain>
    </S.ReviewWritingPage>
  );
};

export default ReviewWritingPage;
