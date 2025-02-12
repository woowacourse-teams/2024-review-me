import Icon from '@/assets/emptyContentIcon.svg';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface EmptyContentProps {
  iconHeight?: string;
  iconWidth?: string;
  iconMessageGap?: string;
  messageFontSize?: string;
  isBorder?: boolean;
}

const EmptyContent = ({
  iconHeight,
  iconWidth,
  iconMessageGap,
  messageFontSize,
  isBorder,
  children,
}: EssentialPropsWithChildren<EmptyContentProps>) => {
  return (
    <S.EmptyContent $iconMessageGap={iconMessageGap} $isBorder={isBorder}>
      <S.Img $height={iconHeight} $width={iconWidth} alt="" src={Icon} />
      <S.MessageContainer $messageFontSize={messageFontSize}>{children}</S.MessageContainer>
    </S.EmptyContent>
  );
};

export default EmptyContent;
