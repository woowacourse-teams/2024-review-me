-- 기존 테이블을 마이그레이션이 완료된 테이블로 대체합니다.

-- 마이그레이션이 테이블에 기존 테이블과 동일한 인덱스를 생성합니다.
CREATE INDEX review_idx_review_group_id ON new_review (review_group_id);

-- 마이그레이션이 테이블에 기존 테이블의 변경 사항을 반영합니다.
ALTER TABLE new_review ADD COLUMN member_id BIGINT NULL;

-- 기존 테이블을 삭제합니다.
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS checkbox_answer;
DROP TABLE IF EXISTS checkbox_answer_selected_option;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS text_answer;
SET FOREIGN_KEY_CHECKS = 1;

-- 마이그레이션이 완료된 테이블의 이름을 변경합니다.
ALTER TABLE new_checkbox_answer RENAME TO checkbox_answer;
ALTER TABLE new_checkbox_answer_selected_option RENAME TO checkbox_answer_selected_option;
ALTER TABLE new_review RENAME TO review;
ALTER TABLE new_text_answer RENAME TO text_answer;
