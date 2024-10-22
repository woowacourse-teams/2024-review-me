import CloseIcon from '@/assets/x.svg';
import { EssentialPropsWithChildren } from '@/types';

import FocusTrap from '../../FocusTrap';
import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

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
    <ModalPortal>
      <ModalBackground closeModal={isClosableOnBackground ? handleClose : null}>
        <FocusTrap>
          <S.ContentModalContainer style={$style}>
            <S.ContentModalHeader>
              <S.Title>{title}</S.Title>
              <S.CloseButton onClick={handleClose}>
                <img src={CloseIcon} alt="모달 닫기" />
              </S.CloseButton>
            </S.ContentModalHeader>
            <S.Contents>{children}</S.Contents>
          </S.ContentModalContainer>
        </FocusTrap>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ContentModal;
