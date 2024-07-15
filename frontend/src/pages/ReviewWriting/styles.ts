import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  justify-content: center;
`;

export const Header = styled.header`
  width: 100%;
  height: 3rem;

  display: flex;
  gap: 1rem;
  align-items: center;
  padding-left: 1rem;

  position: fixed;
  top: 0;

  background-color: #aedaff;
`;

export const Main = styled.main`
  height: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;

  margin: 3rem 0;
`;

export const ReviewWritingForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 40rem;
  height: fit-content;

  padding: 2rem;

  margin-top: 3rem;

  box-shadow: 0px 0px 5px #444;
  border-top: 0.5rem solid #aedaff;
  border-radius: 0.5rem;
`;

export const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;

  gap: 1rem;
  margin-bottom: 3rem;
`;

export const ProjectName = styled.div`
  font-size: 1.5rem;
  font-weight: 700;
`;

export const ReviewInfo = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const ReviewList = styled.ul`
  display: flex;
  flex-direction: column;

  gap: 1rem;

  margin-bottom: 1.5rem;
`;

export const ReviewItem = styled.li`
  display: flex;
  flex-direction: column;
`;

export const ReviewTextarea = styled.textarea`
  width: 100%;
  height: 10rem;
  max-width: 100%;

  padding: 1rem;
  border-radius: 1rem;
  margin-top: 0.5rem;
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
