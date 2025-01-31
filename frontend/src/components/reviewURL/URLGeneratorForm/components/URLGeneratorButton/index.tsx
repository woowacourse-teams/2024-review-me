import { DataForReviewRequestCode } from '@/apis/group';
import { Button, BlinkLoader } from '@/components';
import { HOM_EVENT_NAME } from '@/constants';
import { debounce, trackEventInAmplitude } from '@/utils';

import usePostDataForReviewRequestCode, {
  UsePostDataForReviewRequestCodeProps,
} from '../../hooks/usePostDataForReviewRequestCode';

const DEBOUNCE_TIME = 300;

interface URLGeneratorButtonProps extends UsePostDataForReviewRequestCodeProps {
  isFormValid: boolean;
  dataForReviewRequestCode: DataForReviewRequestCode;
}
const URLGeneratorButton = ({
  isFormValid,
  dataForReviewRequestCode,
  handleAPIError,
  handleAPISuccess,
}: URLGeneratorButtonProps) => {
  const mutation = usePostDataForReviewRequestCode({ handleAPIError, handleAPISuccess });

  const postDataForURL = () => {
    trackEventInAmplitude(HOM_EVENT_NAME.generateReviewURL);

    mutation.mutate(dataForReviewRequestCode, {
      onSuccess: handleAPISuccess,
      onError: handleAPIError,
    });
  };

  const handleURLCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    postDataForURL();
  }, DEBOUNCE_TIME);

  return (
    <Button
      type="button"
      styleType={isFormValid && !mutation.isPending ? 'primary' : 'disabled'}
      onClick={handleURLCreationButtonClick}
      disabled={!isFormValid && !mutation.isPending}
    >
      {mutation.isPending ? <BlinkLoader>리뷰 링크 생성 중...</BlinkLoader> : '리뷰 링크 생성하기'}
    </Button>
  );
};

export default URLGeneratorButton;
