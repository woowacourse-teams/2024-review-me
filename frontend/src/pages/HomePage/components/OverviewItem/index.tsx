import React from 'react';

import * as S from './styles';

export interface OverviewItemStyleProps {
  direction: React.CSSProperties['flexDirection'];
}

interface OverviewItemProps extends OverviewItemStyleProps {
  imageSrc: string;
  title: string;
  description: string[];
}

const OverviewItem = ({ direction, imageSrc, title, description }: OverviewItemProps) => {
  return (
    <S.OverviewItem direction={direction}>
      <S.OverviewItemImg src={imageSrc} alt={title} direction={direction} />
      <S.OverviewItemContent>
        <S.OverviewItemTitle>{title}</S.OverviewItemTitle>
        <S.OverviewItemDescription>
          {description.map((line, index) => (
            <React.Fragment key={index}>
              {line}
              {index < description.length - 1 && <br />}
            </React.Fragment>
          ))}
        </S.OverviewItemDescription>
      </S.OverviewItemContent>
    </S.OverviewItem>
  );
};

export default OverviewItem;
