-- 기존 Template의 중간 테이블을 사용하지 않도록 수정합니다.
-- 1:N이 됨에 따라, N 부분에 foreign key가 필요합니다.

ALTER TABLE section ADD COLUMN template_id BIGINT;
ALTER TABLE question ADD COLUMN section_id BIGINT;
ALTER TABLE question ADD COLUMN option_group_id BIGINT;

-- 기존 테이블의 데이터를 새로운 테이블로 이동
UPDATE question q JOIN section_question sq ON q.id = sq.question_id SET q.section_id = sq.section_id;
UPDATE section s JOIN template_section st ON s.id = st.section_id SET s.template_id = st.template_id;
UPDATE question q JOIN option_group og ON q.id = og.question_id SET q.option_group_id = og.id;

-- FK 관계 설정
ALTER TABLE section ADD CONSTRAINT section_fk_template_id FOREIGN KEY (template_id) REFERENCES template (id);
ALTER TABLE section ADD CONSTRAINT section_fk_on_selected_option_id FOREIGN KEY (on_selected_option_id) REFERENCES option_item (id);
ALTER TABLE question ADD CONSTRAINT question_fk_section_id FOREIGN KEY (section_id) REFERENCES section (id);
ALTER TABLE option_item ADD CONSTRAINT option_item_fk_option_group_id FOREIGN KEY (option_group_id) REFERENCES option_group (id);

-- 혹시 몰라서 DROP TABLE은 하지 않음
