-- 向用户表插入数据
insert into user(id, username, password, role_id) values(1, 'admin', 'admin', 1);

-- 向权限角色表插入角色
insert into role(id, name) values(1, '教师'),(2, '管理员');

