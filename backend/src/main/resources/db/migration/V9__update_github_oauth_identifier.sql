-- oauth 로 인증을 진행할 때 사용자를 식별하기 위한 컬럼을 email 에서 external_id 로 변경합니다.

ALTER TABLE member ADD COLUMN external_id VARCHAR(225) NOT NULL;
ALTER TABLE member DROP COLUMN email;
