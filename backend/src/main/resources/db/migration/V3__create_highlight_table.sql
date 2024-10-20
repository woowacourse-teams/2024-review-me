-- highlight 테이블을 생성합니다.
-- 조회의 성능을 높이고, 인덱스 단위의 잠금으로 여러 사용자가 동시에 테이블에 접근해 수정할 수 있게 answer_id 컬럼에 인덱스를 추가합니다.

CREATE TABLE highlight
(
    id          BIGINT AUTO_INCREMENT,
    answer_id   BIGINT NOT NULL,
    line_index  INT    NOT NULL,
    start_index INT    NOT NULL,
    end_index   INT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX highlight_idx_answer_id ON highlight (answer_id);
