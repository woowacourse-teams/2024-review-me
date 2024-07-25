import styled from '@emotion/styled';

export const KeywordSection = styled.section`
  width: 100%;
  margin-top: 3.2rem;
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  row-gap: 3.2rem;
  column-gap: 2.4rem;
`;

export const KeywordBox = styled.div`
  display: flex;
  align-items: center;

  box-sizing: border-box;
  height: 5rem;
  height: fit-content;
  padding: 0.8rem 2.5rem;

  font-size: 1.6rem;
  line-height: 2.4rem;
  text-align: center;

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border: 0.1rem solid ${({ theme }) => theme.colors.primary};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;
