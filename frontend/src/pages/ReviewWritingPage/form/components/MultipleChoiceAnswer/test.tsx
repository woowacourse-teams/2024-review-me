import { ThemeProvider } from '@emotion/react';
import { fireEvent, render } from '@testing-library/react';
import { RecoilRoot, RecoilState } from 'recoil';

import { REVIEW_QUESTION_DATA } from '@/mocks/mockData';
import { reviewWritingFormSectionListAtom } from '@/recoil';
import theme from '@/styles/theme';
import { ReviewWritingCardQuestion, ReviewWritingQuestionOptionGroup } from '@/types';

import MultipleChoiceAnswer from '.';

const QUESTION: ReviewWritingCardQuestion = REVIEW_QUESTION_DATA.sections[0].questions[0];

interface InitializeStateParams {
  set: <T>(recoilState: RecoilState<T>, newValue: T) => void;
}

const initializeState = ({ set }: InitializeStateParams) => {
  set(reviewWritingFormSectionListAtom, REVIEW_QUESTION_DATA.sections);
};

const renderWithProviders = (question: ReviewWritingCardQuestion) => {
  return render(
    <RecoilRoot initializeState={initializeState}>
      <ThemeProvider theme={theme}>
        <MultipleChoiceAnswer question={question} />
      </ThemeProvider>
    </RecoilRoot>,
  );
};

describe('객관식 최대 선택 테스트', () => {
  it('최대 선택 개수를 넘어선 추가 선택을 할 수 없으며 이를 시도할 경우 안내문구를 띄운다.', () => {
    const renderResult = renderWithProviders(QUESTION);

    //최대 선택
    const { maxCount, options } = QUESTION.optionGroup as ReviewWritingQuestionOptionGroup;

    const optionList = options.slice(0, maxCount);

    optionList.forEach((option) => {
      const checkbox = renderResult.getByTestId(`checkbox-${option.optionId}`) as HTMLInputElement | undefined;
      expect(checkbox).toBeDefined();

      if (!checkbox) return;
      //체크박스 초기 설정 확인
      expect(checkbox).not.toBeChecked();
      // 선택
      fireEvent.click(checkbox);

      expect(checkbox).toBeChecked();
    });

    const overSelectionCheckbox = renderResult.getByTestId(`checkbox-${options[maxCount].optionId}`) as
      | HTMLInputElement
      | undefined;

    expect(overSelectionCheckbox).toBeDefined();

    if (!overSelectionCheckbox) return;
    //초기 설정 확인
    expect(overSelectionCheckbox).not.toBeChecked();
    expect(renderResult.queryByTestId('limitGuideMessage')).not.toBeInTheDocument();
    // 선택
    fireEvent.click(overSelectionCheckbox);

    expect(overSelectionCheckbox).not.toBeChecked();
    expect(renderResult.queryByTestId('limitGuideMessage')).toBeInTheDocument();
  });
});
