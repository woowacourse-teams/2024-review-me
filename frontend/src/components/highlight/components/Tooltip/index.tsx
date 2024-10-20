import { useState } from 'react';

import HelperIcon from '@/assets/helper.svg';
import { isTouchDevice } from '@/utils';

import * as S from './style';

const Tooltip = () => {
  const [isOpenMessage, setIsOpenMessage] = useState(true);
  //onMouseOver={() => setIsOpenMessage(true)} onMouseOut={() => setIsOpenMessage(false)}
  return (
    <S.TooltipButton>
      <S.HelperIcon src={HelperIcon} alt="도움말 아이콘" />
      {isOpenMessage && (
        <S.Message role="tooltip">
          {isTouchDevice() ? (
            <>
              <S.Text>글자를 선택해 형광펜을 적용하거나 삭제할 수 있어요</S.Text>
              <S.Text>형광펜이 적용된 영역은 슬라이드 동작으로 삭제할 수 있어요</S.Text>
            </>
          ) : (
            <>
              <S.Text>드래그하여 형광펜을 적용하거나 삭제할 수 있어요</S.Text>
              <S.Text>형광펜이 적용된 영역은 길게 눌러 삭제할 수 있어요</S.Text>
            </>
          )}
        </S.Message>
      )}
    </S.TooltipButton>
  );
};

export default Tooltip;
