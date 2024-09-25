// 변경 사항 설명: https://github.com/woowacourse-teams/2024-review-me/issues/682
CREATE INDEX review_idx_review_group_id ON review (review_group_id);
CREATE INDEX review_group_idx_review_request_code ON review_group (review_request_code);
