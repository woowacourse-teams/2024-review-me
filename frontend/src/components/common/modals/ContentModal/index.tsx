import CloseIcon from '@/assets/x.svg';
import { EssentialPropsWithChildren } from '@/types';

import Portal from '../../Portal';
import ModalBackground from '../ModalBackground';

import * as S from './styles';

export interface ContentModalStyleProps {
  $style?: React.CSSProperties;
}

interface ContentModalProps extends ContentModalStyleProps {
  title?: string;
  handleClose: () => void;
  isClosableOnBackground?: boolean;
}

const ContentModal = ({
  title,
  handleClose,
  children,
  $style,
  isClosableOnBackground = true,
}: EssentialPropsWithChildren<ContentModalProps>) => {
  return (
    <Portal>
      <ModalBackground closeModal={isClosableOnBackground ? handleClose : null}>
        <S.ContentModalContainer style={$style}>
          <S.ContentModalHeader>
            <S.Title>{title}</S.Title>
            <S.CloseButton onClick={handleClose}>
              <img src={CloseIcon} alt="모달 닫기" />
            </S.CloseButton>
          </S.ContentModalHeader>
          <S.Contents>{children}</S.Contents>
        </S.ContentModalContainer>
      </ModalBackground>
    </Portal>
  );
};

export default ContentModal;
