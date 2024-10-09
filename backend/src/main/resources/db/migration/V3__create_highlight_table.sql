-- highlight 테이블을 생성합니다.

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
