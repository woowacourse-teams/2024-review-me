import React, { useState } from 'react';

import * as S from './style';

interface ImgWithSkeletonProps {
  children: React.ReactElement<React.ImgHTMLAttributes<HTMLImageElement>>;
  imgWidth: string;
  imgHeight: string;
}

const ImgWithSkeleton = ({ children, imgWidth, imgHeight }: ImgWithSkeletonProps) => {
  const [isLoaded, setIsLoaded] = useState(false);

  const handleImgLoad = (event: React.SyntheticEvent<HTMLImageElement, Event>) => {
    if (children.props.onLoad) {
      children.props.onLoad(event);
    }
    setIsLoaded(true);
  };

  return (
    <S.Container $width={imgWidth} $height={imgHeight}>
      {!isLoaded && <S.ImgSkeleton />}
      <S.ImgWrapper $isLoaded={isLoaded}>
        {React.cloneElement(children, {
          onLoad: (event: React.SyntheticEvent<HTMLImageElement, Event>) => handleImgLoad(event),
        })}
      </S.ImgWrapper>
    </S.Container>
  );
};

export default ImgWithSkeleton;
