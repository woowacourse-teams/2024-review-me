-- 기존에 중복된 데이터를 제거합니다.
-- 중복된 review_request_code 중 가장 오래된 하나만 남기고 삭제합니다.

DELETE rg FROM review_group rg
LEFT JOIN (
    SELECT MIN(id) AS min_id
    FROM review_group
    GROUP BY review_request_code
) AS keep ON rg.id = keep.min_id
WHERE keep.min_id IS NULL;


-- 중복이 허용되지 않는 컬럼에 UNIQUE 제약 조건을 추가합니다.

ALTER TABLE review_group MODIFY COLUMN review_request_code VARCHAR(255) NOT NULL UNIQUE;
ALTER TABLE member MODIFY COLUMN external_id VARCHAR(225) NOT NULL UNIQUE;
