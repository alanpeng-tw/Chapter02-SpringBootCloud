#使用者基本資訊主檔
create table user_info(
    id			bigint not null auto_increment,
    user_id		varchar(10)	comment '使用者ID',
    nick_name	varchar(30)	comment '使用者暱稱',
    mobile_no	varchar(10)	comment '使用者註冊手機號碼',
    password	varchar(64)	comment '登入密碼',
    is_login	int			comment '是否登入。 0-未登入; 1-已登入',
    login_time	timestamp default current_timestamp	comment '最近登入時間',
    is_del		int			comment '是否登出。 0-未登出; 1-已登出',
    create_time	timestamp default current_timestamp	comment '建立時間'
    primary key(id)
);
alter table user_info comment '使用者基本資訊主檔';
create index idx_ui_user_id on user_info(user_id);
create index idx_ui_mobile_no on user_info(mobile_no);


#簡訊驗證資訊
create table user_sms_code(
    id			bigint not null auto_increment,
    mobile_no	varchar(10)	comment '使用者註冊手機號碼',
    sms_code	varchar(10)	comment '簡訊驗證碼',
    send_time	timestamp default current_timestamp	comment '簡訊發送時間',
    create_time	timestamp default current_timestamp	comment '建立時間',
    primary key(id)
);
alter table user_sms_code comment '簡訊驗證資訊';
create index idx_usc_mobile_no on user_sms_code(mobile_no);