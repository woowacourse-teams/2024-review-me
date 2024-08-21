import CloseIcon from '@/assets/x.svg';
import { EssentialPropsWithChildren } from '@/types';

import ModalBackground from '../ModalBackground';
import ModalPortal from '../ModalPortal';

import * as S from './styles';

interface ContentModalProps {
  handleClose: () => void;
}

const ContentModal = ({ handleClose, children }: EssentialPropsWithChildren<ContentModalProps>) => {
  return (
    <ModalPortal>
      <ModalBackground closeModal={handleClose}>
        <S.ContentModalContainer>
          <S.ContentModalHeader>
            <S.CloseButton onClick={handleClose}>
              <img src={CloseIcon} alt="모달 닫기" />
            </S.CloseButton>
          </S.ContentModalHeader>
          <S.Contents>{children}</S.Contents>
        </S.ContentModalContainer>
      </ModalBackground>
    </ModalPortal>
  );
};

export default ContentModal;
