import { EmptyContent } from '@/components';

import { useDeviceBreakpoints } from '../../hooks';

const EmptyWrittenReview = () => {
  const { deviceType } = useDeviceBreakpoints();

  return (
    <EmptyContent
      iconWidth={deviceType.isDesktop ? '30vw' : '60vw'}
      messageFontSize={deviceType.isTablet ? '2rem' : undefined}
      iconHeight="45vh"
    >
      <p>아직 작성한 리뷰가 없어요...</p>
    </EmptyContent>
  );
};

export default EmptyWrittenReview;
