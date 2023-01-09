create table comment
(
    id         int auto_increment
        primary key,
    additional int          null,
    good_id    int          null,
    content    varchar(255) null,
    is_delete  bit          not null,
    score      int          null,
    time       datetime     null,
    user_id    int          null,
    main_id    int          null
)
    engine = MyISAM;

create table goods
(
    id            int auto_increment
        primary key,
    name          varchar(45)  not null,
    remark        varchar(45)  null,
    price         varchar(45)  not null,
    uid           varchar(45)  not null,
    goods_type    varchar(255) null,
    sort_by_major varchar(255) null,
    pic           varchar(45)  null,
    state         varchar(45)  null,
    degree        varchar(45)  null,
    address       varchar(45)  null,
    bid           int          null,
    contact       varchar(45)  null,
    semester      varchar(45)  null,
    rate          int          null,
    sortByGoods   varchar(45)  not null,
    buy_time      datetime(6)  null,
    release_time  datetime(6)  null
);

create table goods_weight
(
    id            int auto_increment
        primary key,
    buyer_major   varchar(255) null,
    sort_by_goods varchar(255) null,
    weight        int          null
);

create table request
(
    id              int auto_increment
        primary key,
    address         varchar(255) null,
    contact         varchar(255) null,
    description     varchar(255) null,
    finish_time     datetime(6)  null,
    finish_user_id  int          null,
    major           varchar(255) null,
    middle          varchar(255) null,
    minor           varchar(255) null,
    name            varchar(255) null,
    price           double       null,
    request_time    datetime(6)  null,
    request_user_id int          null,
    state           varchar(255) null,
    time            datetime(6)  null
);

create table user
(
    id                         int auto_increment
        primary key,
    username                   varchar(45)  not null,
    password                   varchar(45)  not null,
    major                      varchar(45)  not null,
    year                       varchar(45)  not null,
    usertype                   varchar(45)  not null,
    registerTime               varchar(45)  not null,
    email                      varchar(255) null,
    goods_bought               int          null,
    goods_sold                 int          null,
    rate_goods                 int          null,
    rate_request               int          null,
    register_time              datetime(6)  null,
    request_published_finished int          null,
    request_taken_finished     int          null
);

