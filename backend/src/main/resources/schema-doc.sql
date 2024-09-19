CREATE TABLE review_group
(
    id                  BIGINT AUTO_INCREMENT,
    group_access_code   VARCHAR(255) NOT NULL,
    project_name        VARCHAR(255) NOT NULL,
    review_request_code VARCHAR(255) NOT NULL,
    reviewee            VARCHAR(255) NOT NULL,
    template_id         BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE review
(
    id              BIGINT AUTO_INCREMENT,
    created_at      TIMESTAMP(6) NOT NULL,
    review_group_id BIGINT       NOT NULL,
    template_id     BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE template
(
    id BIGINT AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE section
(
    id                    BIGINT AUTO_INCREMENT,
    header                VARCHAR(1000)                 NOT NULL,
    on_selected_option_id BIGINT,
    position              INTEGER                       NOT NULL,
    section_name          VARCHAR(255)                  NOT NULL,
    visible_type          VARCHAR(255)                  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE template_section
(
    id          BIGINT AUTO_INCREMENT,
    section_id  BIGINT NOT NULL,
    template_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (template_id) REFERENCES template (id)
);

CREATE TABLE question
(
    id            BIGINT AUTO_INCREMENT,
    content       VARCHAR(1000)            NOT NULL,
    guideline     VARCHAR(1000),
    position      INTEGER                  NOT NULL,
    question_type VARCHAR(255)             NOT NULL,
    required      BOOLEAN                  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE section_question
(
    id          BIGINT AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    section_id  BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (section_id) REFERENCES section (id)
);

CREATE TABLE text_answer
(
    id          BIGINT AUTO_INCREMENT,
    content     VARCHAR(5000) NOT NULL,
    question_id BIGINT        NOT NULL,
    review_id   BIGINT        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (review_id) REFERENCES review (id)
);

CREATE TABLE checkbox_answer
(
    id          BIGINT AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    review_id   BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (review_id) REFERENCES review (id)
);

CREATE TABLE checkbox_answer_selected_option
(
    id                 BIGINT AUTO_INCREMENT,
    checkbox_answer_id BIGINT NOT NULL,
    selected_option_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (checkbox_answer_id) REFERENCES checkbox_answer (id)
);

CREATE TABLE option_group
(
    id                  BIGINT AUTO_INCREMENT,
    max_selection_count INTEGER NOT NULL,
    min_selection_count INTEGER NOT NULL,
    question_id         BIGINT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE option_item
(
    id              BIGINT AUTO_INCREMENT,
    content         VARCHAR(255)                NOT NULL,
    option_group_id BIGINT                      NOT NULL,
    option_type     VARCHAR(255)                NOT NULL,
    position        INTEGER                     NOT NULL,
    PRIMARY KEY (id)
);
