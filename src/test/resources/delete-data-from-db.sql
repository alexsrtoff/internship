

    alter table order_items
       drop
       foreign key FKbioxgbv59vetrxe0ejfubep1w;

    alter table order_items
       drop
       foreign key FKocimc7dtr037rh4ls4l95nlfi;

    alter table orders
       drop
       foreign key FKm2dep9derpoaehshbkkatam3v;

    drop table if exists clients;

    drop table if exists order_items;

    drop table if exists orders;

    drop table if exists products;