import styled from '@emotion/styled';

export const ReviewWritingPage = styled.form`
  display: flex;
  flex-direction: column;
  width: 80rem;
  height: fit-content;
  border-top: 0.5rem solid #aedaff;
`;

export const ReviewFormHeader = styled.header`
  display: flex;
  flex-direction: column;

  gap: 1rem;
  margin-bottom: 3rem;
`;

export const InfoContainer = styled.div`
  display: flex;

  gap: 1.5rem;
`;

export const Container = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;

  width: 100%;
`;

export const LogoImage = styled.img`
  width: 6rem;
  height: 6rem;
`;

export const ProjectName = styled.div`
  font-size: 2rem;
  font-weight: 700;
`;

export const ReviewInfo = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Reviewee = styled.div`
  & > span {
    color: ${({ theme }) => theme.colors.primary};
  }
`;

export const ReviewExpirationDate = styled.div`
  display: flex;

  gap: 0.5rem;
`;

export const ReviewFormMain = styled.main`
  display: flex;
  flex-direction: column;
`;

export const ReviewContainer = styled.section`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2.4rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;

  gap: 1rem;
`;

export const Button = styled.button`
  width: 3.5rem;
  height: 2rem;

  border: 0.0625rem solid black;
`;

export const KeywordContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const KeywordTitle = styled.div`
  font-size: 1.2rem;
  font-weight: 600;
`;

export const KeywordList = styled.ul`
  display: flex;
  flex-direction: column;

  gap: 1rem;

  margin: 1rem 0;
`;

export const KeywordItem = styled.li`
  display: flex;
  align-items: center;

  gap: 0.5rem;

  & > input {
    width: 1rem;
    height: 1rem;
    top: 0.09375rem;
  }
`;
