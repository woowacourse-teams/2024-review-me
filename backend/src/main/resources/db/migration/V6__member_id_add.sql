-- 리뷰 그룹에 memberId를 추가합니다.
-- 리뷰 그룹의 group_access_code에 null을 허용합니다.

ALTER TABLE review_group ADD COLUMN member_id BIGINT NULL;
ALTER TABLE review_group MODIFY COLUMN group_access_code VARCHAR(255) NULL:

-- 리뷰에 memberId를 추가합니다.
ALTER TABLE review ADD COLUMN member_id BIGINT NULL;
