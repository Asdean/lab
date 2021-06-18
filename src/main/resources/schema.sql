-- 创建用户表
create table if not exists user
(
    id bigint(19) not null primary key,
    username varchar(45) not null default '' comment '登录账户名',
    password varchar(150) not null default '' comment '登录密码',
    role_id bigint(19) not null default 1 comment '角色id',
    create_time datetime not null default current_timestamp() comment '注册时间',
    update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间'
);

-- 创建权限角色表
create table if not exists role
(
    id bigint(19) not null primary key,
    name varchar(20) not null default '' comment '角色名称',
    create_time datetime not null default current_timestamp() comment '创建时间',
    update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间'
);

-- 创建实验室表
create table if not exists lab
(
    id bigint(19) primary key,
    name varchar(30) not null default '' comment '实验室名称',
    location  varchar(20) not null default '' comment '实验室位置',
    number int not null default 0 comment '实验室人数',
    memo text comment '备注',
    create_time datetime not null default current_timestamp() comment '创建时间',
    update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间'
);

-- 创建课程表
create table if not exists course
(
    id bigint(19) primary key auto_increment,
    name varchar(30) not null default '' comment '课程名称',
    student_amount int not null default 0 comment '上课人数',
    lab_id bigint(19) not null default 0 comment '实验室id',
    memo text comment '备注',
    create_time datetime not null default current_timestamp() comment '创建时间',
    update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间',
    index(lab_id)
);

-- 创建预约记录表
# create table if not exists booking_record
# (
#     id bigint(19) primary key auto_increment,
#     lab_id bigint(19) not null default 0 comment '实验室id',
#     course_id bigint(19) not null default 0 comment '实验课程id',
#     memo text comment '备注',
#     create_time datetime not null default current_timestamp() comment '创建时间',
#     update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间',
#     index(lab_id),
#     index(course_id)
# );

-- 创建预约记录所选时间表
create table if not exists booking_record
(
    id bigint(19) primary key auto_increment,
    course_id bigint(19) not null default 0 comment '课程id',
    week_no int not null default 0 comment '周编号 1-18',
    section_no int not null default 0 comment '节编号 1-4',
    weekday int not null default 0 comment '星期编号 1-7',
    create_time datetime not null default current_timestamp() comment '创建时间',
    update_time datetime not null default current_timestamp() on update current_timestamp comment '更新时间',
    index(course_id)
);


