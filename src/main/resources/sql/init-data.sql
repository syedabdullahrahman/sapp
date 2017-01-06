INSERT INTO users(userid,username,password,enabled)
VALUES ('001','Admin','$2a$04$lv/KdPIsH19vP0VUJhdn4e3iksPzy.NwJiY73jHXKwR162edrAVpG', true);
INSERT INTO users(userid,username,password,enabled)
VALUES ('002','User','$2a$04$VYg7rxx7ZKLszJbLxAW48eMu/cYy8Asg4BFmOkEawwK6WfuywISdS', true);

INSERT INTO user_roles (userid, role)
VALUES ('002', 'ROLE_USER');
INSERT INTO user_roles (userid, role)
VALUES ('001', 'ROLE_ADMIN');
INSERT INTO user_roles (userid, role)
VALUES ('001', 'ROLE_USER');