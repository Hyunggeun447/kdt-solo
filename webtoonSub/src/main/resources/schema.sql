create table webtoons
(
    webtoon_id   binary(16)   not null
        primary key,
    webtoon_name varchar(30)  not null,
    save_path    varchar(200) not null,
    author_id    binary(16)   not null,
    webtoon_type varchar(50)  not null,
    description  varchar(500) null,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  null,
    constraint webtoon_name
        unique (webtoon_name)
);

create table customers
(
    customer_id              binary(16) not null
        primary key,
    customer_email           varchar(30) not null,
    password                 varchar(30) not null,
    wallet                   int not null,
    expiry_subscription_date datetime(6) null,
    created_at               datetime(6) not null,
    updated_at               datetime(6) null,
    last_login_at            datetime(6) null,
    constraint customer_email
        unique (customer_email)
);



