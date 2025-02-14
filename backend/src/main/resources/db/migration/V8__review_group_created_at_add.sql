-- 리뷰 그룹에 created_at을 추가합니다.

ALTER TABLE review_group ADD COLUMN created_at TIMESTAMP(6) NOT NULL;
