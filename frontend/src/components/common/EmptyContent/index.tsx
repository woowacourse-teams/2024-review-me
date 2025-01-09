import Icon from '@/assets/emptyContentIcon.svg';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface EmptyContentProps {
  iconHeight?: string;
  iconWidth?: string;
  iconMessageGap?: string;
  messageFontSize?: string;
}

const EmptyContent = ({
  iconHeight,
  iconWidth,
  iconMessageGap,
  messageFontSize,
  children,
}: EssentialPropsWithChildren<EmptyContentProps>) => {
  return (
    <S.EmptyContent $iconMessageGap={iconMessageGap}>
      <S.Img $height={iconHeight} $width={iconWidth} alt="" src={Icon} />
      <S.MessageContainer $messageFontSize={messageFontSize}>{children}</S.MessageContainer>
    </S.EmptyContent>
  );
};

export default EmptyContent;
