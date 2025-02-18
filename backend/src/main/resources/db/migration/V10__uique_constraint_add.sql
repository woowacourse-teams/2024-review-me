-- 중복이 허용되지 않는 컬럼에 UNIQUE 제약 조건을 추가합니다.

ALTER TABLE review_group MODIFY COLUMN review_request_code VARCHAR(255) NOT NULL UNIQUE;
ALTER TABLE member MODIFY COLUMN external_id VARCHAR(225) NOT NULL UNIQUE;
