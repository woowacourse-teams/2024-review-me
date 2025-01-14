import React, { useState } from 'react';

import * as S from './style';

interface ImgWithSkeletonProps {
  children: React.ReactElement<React.ImgHTMLAttributes<HTMLImageElement>>;
  imgWidth: string;
  imgHeight: string;
}

const ImgWithSkeleton = ({ children, imgWidth, imgHeight }: ImgWithSkeletonProps) => {
  const [isLoaded, setIsLoaded] = useState(false);

  const handleImgLoad = () => {
    setIsLoaded(true);
  };

  return (
    <S.Container $width={imgWidth} $height={imgHeight}>
      {!isLoaded && <S.ImgSkeleton />}
      <S.ImgWrapper $isLoaded={isLoaded}>
        {React.cloneElement(children, {
          onLoad: handleImgLoad,
        })}
      </S.ImgWrapper>
    </S.Container>
  );
};

export default ImgWithSkeleton;
