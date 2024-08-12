import { useState } from 'react';

import AnswerListPreviewModal from '@/components/AnswerListPreviewModal';
import { ANSWER_LIST } from '@/components/AnswerListPreviewModal/answerList';

const TempPage = () => {
  const [isOpen, setIsOpen] = useState(true);

  const onClick = () => {
    setIsOpen(true);
  };

  return (
    <div>
      <button onClick={onClick} type="button">
        모달버튼
      </button>
      {isOpen && (
        <AnswerListPreviewModal
          answerList={ANSWER_LIST}
          closeModal={() => {
            setIsOpen(false);
          }}
        ></AnswerListPreviewModal>
      )}
    </div>
  );
};

export default TempPage;
