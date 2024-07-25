import React from 'react';

interface MultilineTextViewerProps {
  text: string;
}

const MultilineTextViewer = ({ text }: MultilineTextViewerProps) => {
  return (
    <>
      {text.split('\n').map((line, index) => (
        <React.Fragment key={index}>
          {line}
          <br />
        </React.Fragment>
      ))}
    </>
  );
};

export default MultilineTextViewer;
