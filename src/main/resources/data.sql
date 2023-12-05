-- INSERT INTO users (username, password, role) VALUES ('One', crypt('11', gen_salt('bf')), '{User}');
INSERT INTO users (username, password, role) VALUES ('2', '12', '{User}');
INSERT INTO users (username, password, role) VALUES ('Three', '13', '{User}');
INSERT INTO users (username, password, role) VALUES ('Four', '14', '{User}');
INSERT INTO users (username, password, role) VALUES ('Five', '15', '{User}');

INSERT INTO tasks (task, task_status, user_id) VALUES ('One', 'PLANNED', '1');
INSERT INTO tasks (task, task_status, user_id) VALUES ('One', 'PLANNED', '2');
INSERT INTO tasks (task, task_status, user_id) VALUES ('Two', 'IN_PROGRESS', '3');
INSERT INTO tasks (task, task_status, user_id) VALUES ('Three', 'DONE', '2');
INSERT INTO tasks (task, task_status, user_id) VALUES ('Two', 'IN_PROGRESS', '3');
INSERT INTO tasks (task, task_status, user_id) VALUES ('Six', 'NOTIFIED', '3');
INSERT INTO tasks (task, task_status, user_id) VALUES ('Five', 'NOTIFIED', '1');
