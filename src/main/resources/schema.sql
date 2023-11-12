create table if not exists Shaurme_order (
    id identity,
    delivery_Name varchar(50) not null,
    delivery_Phone varchar(20) not null,
    delivery_Street varchar(50) not null,
    delivery_Apartment_Number varchar(50) not null,
    delivery_Entrance varchar(5),
    delivery_Floor varchar(3),
    cc_Number varchar(16) not null,
    cc_Expiration varchar(5) not null,
    cc_Cvv varchar(3) not null,
    placed_At timestamp not null
);

create table if not exists Shaurme (
    id identity,
    name varchar(50) not null,
    shaurme_order bigint not null,
    shaurme_order_key bigint not null,
    created_at timestamp not null
);

alter table Shaurme
    add foreign key (shaurme_order) references Shaurme_order(id);

create table if not exists Ingredient_Ref (
    ingredient varchar(4) not null,
    shaurme bigint not null,
    shaurme_key bigint not null
);

create table if not exists Ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10) not null
);
alter table Ingredient
    add primary key (ID);

alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);