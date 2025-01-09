import { DataForReviewRequestCode } from '@/apis/group';
import { Button } from '@/components';
import { HOM_EVENT_NAME } from '@/constants';
import usePostDataForReviewRequestCode, {
  UsePostDataForReviewRequestCodeProps,
} from '@/pages/HomePage/hooks/usePostDataForReviewRequestCode';
import { debounce, trackEventInAmplitude } from '@/utils';

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

  const handleUrlCreationButtonClick = debounce((event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    postDataForURL();
  }, DEBOUNCE_TIME);

  return (
    <Button
      type="button"
      styleType={isFormValid && !mutation.isPending ? 'primary' : 'disabled'}
      onClick={handleUrlCreationButtonClick}
      disabled={!isFormValid && !mutation.isPending}
    >
      {mutation.isPending ? '리뷰 링크 생성 중...' : '리뷰 링크 생성하기'}
    </Button>
  );
};

export default URLGeneratorButton;
