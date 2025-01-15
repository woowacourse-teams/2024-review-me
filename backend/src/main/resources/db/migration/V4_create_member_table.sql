-- member 테이블을 생성합니다.

CREATE TABLE member
(
    id    BIGINT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    primary key (id)
);
