-- 리뷰 그룹의 zero date value 를 제거합니다.
-- 리뷰 그룹의 created_at 컬럼의 기본값을 CURRENT_TIMESTAMP(6)으로 변경합니다.

UPDATE review_group
SET created_at = '2000-01-01 00:00:00.000000'
WHERE CAST(created_at AS CHAR) = '0000-00-00 00:00:00.000000';

ALTER TABLE review_group
MODIFY created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6);