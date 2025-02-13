-- 이전에 review 테이블 변경 사항 중 new_review가 아닌 review에 적용된 사항을 다시 반영합니다.
ALTER TABLE new_review ADD COLUMN member_id BIGINT NULL;

-- 기존 review 테이블을 삭제하고, new_review 테이블의 이름을 "review"로 변경합니다.
DROP TABLE IF EXISTS review;
ALTER TABLE new_review RENAME TO review;
