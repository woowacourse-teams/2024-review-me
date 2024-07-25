import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { getDataToWriteReviewApi, postReviewApi } from '@/apis/review';
import ClockLogo from '@/assets/clock.svg';
import GithubLogo from '@/assets/githubLogo.svg';
import Button from '@/components/common/Button';
import { REVIEW } from '@/constants/review';
import { Keyword, ReviewContent, ReviewData, WritingReviewInfoData } from '@/types';

import KeywordButton from './components/KeywordButton';
import RevieweeComment from './components/RevieweeComment';
import ReviewItem from './components/ReviewItem';
import * as S from './styles';

const DUMMY = {
  questionList: [
    { id: 0, content: '동료의 개발 역량 향상을 위해 피드백을 남겨 주세요.' },
    { id: 1, content: '동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요.' },
    { id: 2, content: '팀 동료로 근무한다면 같이 일하고 싶은 개발자인가요?' },
  ],
  keywords: [
    { id: 0, content: '성실해요' },
    { id: 1, content: '잘 먹어요' },
    { id: 2, content: '호감이에요' },
    { id: 3, content: '시간 약속을 잘 지켜요' },
    { id: 4, content: '열정 가득해요' },
    { id: 5, content: '또 함께하고 싶어요' },
  ],
};

const ReviewWritingPage = () => {
  const navigate = useNavigate();

  const [dataToWrite, setDataToWrite] = useState<WritingReviewInfoData | null>(null);
  const [answers, setAnswers] = useState<ReviewContent[]>([
    { questionId: 0, answer: '' },
    { questionId: 1, answer: '' },
    { questionId: 2, answer: '' },
  ]);
  const [selectedKeywords, setSelectedKeywords] = useState<number[]>([]);

  const isValidAnswersLength = !answers.some((id) => id.answer.length < REVIEW.answerMinLength);
  const isValidKeywordSelection =
    selectedKeywords.length >= REVIEW.keywordMinCount && selectedKeywords.length <= REVIEW.keywordMaxCount;
  const isValidForm = isValidAnswersLength && isValidKeywordSelection;

  // useEffect(() => {
  //   const getDataToWrite = async () => {
  //     const data = await getDataToWriteReview(1); // id: 임의 값
  //     setDataToWrite(data);
  //     setAnswers(data.questions.map((question) => ({ questionId: question.id, answer: '' })));
  //   };

  //   getDataToWrite();
  // }, []);

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

    const reviewData: ReviewData = {
      reviewerId: 8,
      reviewerGroupId: 5,
      reviewContents: answers,
      keywords: selectedKeywords,
    };

    console.log(reviewData);
    navigate('/user/review-writing-complete', { replace: true });

    // try {
    //   await postReviewApi({ reviewData });
    //   navigate('/user/review-writing-complete', { replace: true });
    // } catch (error) {
    //   console.error('Failed to submit review:', error);
    // }
  };

  // if (!dataToWrite) return <div>Loading...</div>;

  return (
    <S.ReviewWritingPage onSubmit={handleSubmitReview}>
      <S.ReviewFormHeader>
        <S.ReviewInfoContainer>
          <S.LogoImage src={GithubLogo} alt="깃허브 로고" />
          <S.Container>
            <S.ProjectName>2024-review-me</S.ProjectName>
            {/* <S.ProjectName>{dataToWrite && dataToWrite.reviewerGroup.name}</S.ProjectName> */}
            <S.ReviewInfo>
              <S.Reviewee>
                <span>chysis</span>님을 리뷰해주세요!
              </S.Reviewee>
              {/* <S.Reviewee>
                <span>{dataToWrite && dataToWrite.reviewerGroup.reviewee.name}</span>님을 리뷰해주세요!
              </S.Reviewee> */}
              <S.ReviewExpirationDate>
                <S.ClockImage src={ClockLogo} alt="시계" />
                리뷰 마감일: 2024-08-05
              </S.ReviewExpirationDate>
              {/* <S.ReviewExpirationDate>
                <S.ClockImage src={ClockLogo} alt="시계" />
                리뷰 마감일: {dataToWrite && dataToWrite.reviewerGroup.deadline.toDateString()}
              </S.ReviewExpirationDate> */}
            </S.ReviewInfo>
          </S.Container>
        </S.ReviewInfoContainer>
        <RevieweeComment text={''}></RevieweeComment>
      </S.ReviewFormHeader>
      <S.ReviewFormMain>
        <S.ReviewContainer>
          {DUMMY.questionList.map((question, index) => {
            return (
              <ReviewItem
                question={`${index + 1}. ${question.content}`}
                key={question.id}
                answerValue={answers.find((answer) => answer.questionId === question.id)?.answer || ''}
                handleWrite={(value) => handleAnswerChange(question.id, value)}
              />
            );
          })}
          {/* {dataToWrite &&
            dataToWrite.questions.map((question, index) => {
              return (
                <ReviewItem
                  question={`${index + 1}. ${question.content}`}
                  key={question.id}
                  answerValue={answers.find((answer) => answer.questionId === question.id)?.answer || ''}
                  onWrite={(value) => handleAnswerChange(question.id, value)}
                />
              );
            })} */}
        </S.ReviewContainer>
        <S.KeywordContainer>
          <S.KeywordTitle>{DUMMY.questionList.length + 1}. 키워드</S.KeywordTitle>
          <S.KeywordList>
            {/* {dataToWrite &&
              dataToWrite.keywords.map((keyword) => {
                return (
                  <KeywordButton
                    isSelected={selectedKeywords.includes(keyword.id)}
                    key={keyword.id}
                    onClick={() => handleKeywordButtonClick(keyword)}
                  >
                    {keyword.content}
                  </KeywordButton>
                );
              })} */}
            {DUMMY.keywords.map((keyword) => {
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
