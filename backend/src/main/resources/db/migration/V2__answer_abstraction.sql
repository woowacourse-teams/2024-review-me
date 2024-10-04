-- answer 테이블을 추상화합니다. 아래와 같은 5개의 마이그레이션 테이블을 생성합니다.
-- ALTER TABLE로 dev, prod에 auto_increment를 특정 수로 초기화해야 합니다.

CREATE TABLE new_review (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP(6) NOT NULL,
    review_group_id BIGINT NOT NULL,
    template_id BIGINT NOT NULL,
    PRIMARY KEY (id),
);

CREATE TABLE answer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    review_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (review_id) REFERENCES new_review (id),
);

CREATE TABLE new_checkbox_answer (
    id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES answer (id),
);

CREATE TABLE new_text_answer (
    id BIGINT NOT NULL,
    content varchar(5000) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES answer (id),
);

CREATE TABLE new_checkbox_answer_selected_option (
    id BIGINT NOT NULL AUTO_INCREMENT,
    checkbox_answer_id BIGINT NOT NULL,
    selected_option_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (checkbox_answer_id) REFERENCES new_checkbox_answer (id),
)
