import styled from '@emotion/styled';

export const ReviewWritingForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: fit-content;
  border-top: 0.5rem solid #aedaff;
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

  margin-bottom: 1rem;

  & > div {
    font-weight: 600;
  }
`;

export const ReviewTextarea = styled.textarea`
  width: 100%;
  height: 10rem;
  max-width: 100%;

  padding: 1rem;
  border-radius: 1rem;
  margin-top: 0.5rem;

  resize: none;
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
