create table popular_alcohol
(
    id            bigint auto_increment comment '기본 키'
        primary key,
    alcohol_id    bigint                              not null comment '술 ID',
    year          smallint                            not null comment '년도',
    month         tinyint                             not null comment '월',
    day           tinyint                             not null comment '일',
    review_score  decimal(5, 2)                       not null comment '리뷰 점수',
    rating_score  decimal(5, 2)                       not null comment '평점 점수',
    pick_score    decimal(5, 2)                       not null comment '찜하기 점수',
    popular_score decimal(5, 2)                       not null comment '인기도 점수',
    created_at    timestamp default CURRENT_TIMESTAMP null comment '생성일시',
    constraint uniq_alcohol_year_month
        unique (alcohol_id, year, month, day)
)
    comment '술 인기도 통계 테이블' charset = utf8mb4;
