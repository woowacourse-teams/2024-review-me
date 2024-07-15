import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Emotion = styled.p`
  font-size: 30px;
  display: inline-block;
  background-color: #413939;
  padding: 10px;
  color: #a0cc39;
`;

const App = () => {
  return (
    <>
      <h1
        css={css`
          font-size: 50px;
          background-color: #413939;
          width: 350px;
          padding: 10px;
          color: #69ffcd;
        `}
      >
        Hello emotion
      </h1>
      <Emotion>스타일이 적용된 리액트 컴포넌트</Emotion>
    </>
  );
};

export default App;
