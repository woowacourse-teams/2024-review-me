import CopyIcon from '@/assets/copy.svg';

import * as S from './styles';

interface CopyTextButtonProps {
  targetText: string;
  alt: string;
}

const CopyTextButton = ({ targetText, alt }: CopyTextButtonProps) => {
  const handleCopyTextButtonClick = async () => {
    try {
      await navigator.clipboard.writeText(targetText);
      alert('텍스트가 클립보드에 복사되었습니다.');
    } catch (error) {
      if (error instanceof Error) throw new Error('텍스트 복사에 실패했습니다.');
    }
  };

  return (
    <S.CopyTextButton onClick={handleCopyTextButtonClick}>
      <img src={CopyIcon} alt={alt} />
    </S.CopyTextButton>
  );
};

export default CopyTextButton;
