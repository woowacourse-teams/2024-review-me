import { ContentState, convertToRaw, EditorState, Modifier } from 'draft-js';
import { useState } from 'react';
import { Editor } from 'react-draft-wysiwyg';

import { Button } from '@/components';

const MOCK_DATA =
  '나는 말야, 버릇이 하나있어, 그건 매일 잠에 들 시간마다잘 모아둔 기억 조각들 잡히는 걸 집은 후 혼자 조용히 꼬꼬무\n이걸 난\n이름으로 지었어, 고민,\n아무튼, 뭐, 오늘은 하필이면\n너가 스쳐버려서 우리였을 때로\n우리 정말 좋았던 그때로\n우리의 에피소드가 찬란하게 막을 연다\n배경은 너의 집 앞, 첫 데이트가 끝난\n둘만의 에피소드가 참 예쁜 얘기로 시작\n자작자작, 조심스런 대화, 그새 늦은 시간';

interface StyleBlock {
  index: number;
  offset: number;
  length: number;
}

const EditorTestPage = () => {
  // 줄바꿈을 '\n'으로 변경
  const contents = ContentState.createFromText(MOCK_DATA);
  const [editorState, setEditorState] = useState(() => EditorState.createWithContent(contents));
  //const [editorState, setEditorState] = useState(() => EditorState.createWithContent(contentState));
  const [editedContents, setEditedContents] = useState(() => EditorState.createEmpty());

  // 버튼 클릭 시 형광펜 스타일을 토글하는 함수
  const toggleHighlight = () => {
    const currentStyle = editorState.getCurrentInlineStyle();

    // 'HIGHLIGHT' 스타일이 적용되어 있으면 제거, 없으면 추가
    if (currentStyle.has('HIGHLIGHT')) {
      removeHighlight();
    } else {
      addHighlight();
    }
  };

  // 고정된 형광펜 색상을 적용하는 함수
  const addHighlight = () => {
    const selection = editorState.getSelection();
    const contentState = editorState.getCurrentContent();

    const contentStateWithHighlight = Modifier.applyInlineStyle(contentState, selection, 'HIGHLIGHT');

    const newEditorState = EditorState.push(editorState, contentStateWithHighlight, 'change-inline-style');
    setEditorState(newEditorState);
  };

  // 형광펜 스타일을 제거하는 함수
  const removeHighlight = () => {
    const selection = editorState.getSelection();
    const contentState = editorState.getCurrentContent();

    const contentStateWithoutHighlight = Modifier.removeInlineStyle(contentState, selection, 'HIGHLIGHT');

    const newEditorState = EditorState.push(editorState, contentStateWithoutHighlight, 'change-inline-style');

    setEditorState(newEditorState);
  };
  const getNewStyleBlockList = () => {
    const contentState = editorState.getCurrentContent();
    const rawContent = convertToRaw(contentState);
    const list: StyleBlock[] = [];

    rawContent.blocks.forEach((item, index) => {
      if (item.inlineStyleRanges[0]) {
        const { offset, length } = item.inlineStyleRanges[0];
        list.push({ index, offset, length });
      }
    });
    return list;
  };

  const updateEditedContents = (list: StyleBlock[]) => {
    let contentState = ContentState.createFromText(MOCK_DATA);

    list.forEach(({ index, offset, length }) => {
      const blockKey = contentState.getBlockMap().toList().get(index).getKey();
      const selection = contentState.getSelectionAfter().merge({
        anchorKey: blockKey,
        anchorOffset: offset,
        focusKey: blockKey,
        focusOffset: offset + length,
      });

      // Apply inline style "HIGHLIGHT" to the selected range
      contentState = Modifier.applyInlineStyle(contentState, selection, 'HIGHLIGHT');
    });

    setEditedContents(EditorState.createWithContent(contentState));
  };

  const handleSave = () => {
    const newBlockStyleList = getNewStyleBlockList();
    updateEditedContents(newBlockStyleList);
  };

  return (
    <div>
      <h2>에디터</h2>
      <Button styleType="primary" onClick={toggleHighlight}>
        형광펜 적용/제거
      </Button>
      <br />
      <Button styleType="secondary" onClick={handleSave}>
        저장
      </Button>
      <Editor
        editorState={editorState}
        onEditorStateChange={setEditorState} // 에디터 상태 변경 핸들러
        toolbar={{ options: [] }} // 툴바 옵션 비활성화
        customStyleMap={{
          HIGHLIGHT: { backgroundColor: '#E6E3F6' },
        }}
      />
      <h2>형광펜 색칠 된 결과물</h2>
      <Editor
        editorState={editedContents}
        toolbar={{ options: [] }}
        customStyleMap={{
          HIGHLIGHT: { backgroundColor: '#E6E3F6' },
        }}
      />
    </div>
  );
};

export default EditorTestPage;
