import { URLGeneratorForm, ReviewCard } from '@/components';

import ReviewLinkLayout from '../layouts/ReviewLinkLayout';

import * as S from './styles';

const ReviewLinkDashboard = () => {
  return (
    <S.ReviewLinkDashboardContainer>
      <S.FormSection>
        <URLGeneratorForm />
      </S.FormSection>
      <S.Separator />
      <S.LinkSection>
        <ReviewLinkLayout
          title="생성한 리뷰 링크를 확인해보세요"
          subTitle="클릭하면 해당 프로젝트의 리뷰 목록으로 이동해요"
        >
          {/* TODO: 생성한 리뷰 링크가 없을 경우, 돋보기 컴포넌트 추가 */}
          <ReviewCard
            createdAt="2024-01-15"
            contentPreview="임시용"
            categories={[
              { optionId: 2, content: '💡 문제 해결 능력' },
              { optionId: 1, content: '🗣️ 커뮤니케이션 능력' },
            ]}
            handleClick={() => {}}
          />
        </ReviewLinkLayout>
      </S.LinkSection>
    </S.ReviewLinkDashboardContainer>
  );
};

export default ReviewLinkDashboard;
