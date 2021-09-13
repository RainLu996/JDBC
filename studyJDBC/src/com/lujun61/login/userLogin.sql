drop table if exists t_login;
create table t_login(
    id int primary key auto_increment,
    login_name varchar(255) unique,
    login_password varchar(255),
    real_name varchar(255)
);

insert into t_login(login_name, login_password, real_name) values ('admin', '123', '管理员');
insert into t_login(login_name, login_password, real_name) values ('zhangsan', '123', '张三');

select * from t_login;