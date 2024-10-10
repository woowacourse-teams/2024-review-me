-- highlight 테이블을 생성합니다.
-- 조회의 성능을 높이고, 인덱스 단위의 잠금으로 여러 사용자가 동시에 테이블에 접근해 수정할 수 있게 review_group_id + questionId 복합 인덱스를 추가합니다.

CREATE TABLE highlight
(
    id              BIGINT AUTO_INCREMENT,
    questionId      BIGINT NOT NULl,
    review_group_id BIGINT NOT NULl,
    answer_id       BIGINT NOT NULl,
    line_index      BIGINT NOT NULl,
    start_index     BIGINT NOT NULl,
    end_index       BIGINT NOT NULl,
    PRIMARY KEY (id)
);

CREATE INDEX highlight_idx_review_group_id_question_id ON highlight(review_group_id, questionId);
