-- 向用户表插入数据
insert into user(id, username, password) values(1, 'admin', 'admin');

-- 向权限角色表插入角色
insert into role(id, name) values(1, '管理员'),(2, '教师');

-- 向用户角色关系表插入用户角色对应关系
insert into user_role(id, user_id, role_id) values(1, 1, 1);