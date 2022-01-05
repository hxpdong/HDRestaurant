
drop database if exists restaurant;
create database restaurant;
use restaurant;
SET GLOBAL log_bin_trust_function_creators = 1;
set sql_safe_updates = 0;
/*
bigint unsigned: totalSal, invoice_id, invoice_total
*/
-- -----Huong Duong Restaurant Management Software
-- --STAFF

-- Table staff position
create table Staff_Position(
	post_id int unsigned auto_increment,
    postName varchar(15) unique,
    basic_sal int unsigned default 0,
    constraint ck_staff_position_sal check(basic_sal >= 0),
    primary key (post_ID)
);
insert into Staff_Position(postName) values ("Admin");
insert into Staff_Position(postName, basic_sal) values ("Cashier", 8000000);
insert into Staff_Position(postName, basic_sal) values ("Chef", 9000000);
insert into Staff_Position(postName, basic_sal) values ("Waiter", 8000000);
insert into Staff_Position(postName, basic_sal) values ("Guard", 6500000);
insert into Staff_Position(postName, basic_sal) values ("Labourer", 6500000);

delimiter $$
create procedure position_new(in postName varchar(15), in basic_sal int unsigned)
begin
	declare vpostName varchar(15) default null;
	declare exit handler for sqlexception rollback;
    
    if not exists(select postName from staff_position where upper(staff_position.postname)=upper(postname)) then
		set autocommit = 0;
        start transaction;
        select  CONCAT(UCASE(LEFT(postName, 1)), 
                             LCASE(SUBSTRING(postName, 2))) into vpostName;
        insert into staff_position(postname, basic_sal) values(vpostName, basic_sal);
        commit;
        set autocommit = 1;
        
	end if;
end; $$
delimiter ;

delimiter $$
create procedure position_update(in postName varchar(15), in basic_sal int unsigned)
begin
	declare vpid int unsigned default 0;
	declare exit handler for sqlexception rollback;
	set autocommit = 0;
    select post_id into vpid from staff_position stp where stp.postname=postname;
    
    update staff_position set staff_position.basic_sal=basic_sal where staff_position.post_id=vpid;
    update staff set totalSal=basic_sal+(basic_sal*staff.bonus/100) where staff.post_id=vpid
    and staff.staffState=1;
    commit;
    set autocommit = 1;
    
end; $$
delimiter ;


delimiter $$
create procedure list_position()
begin
	select postName from staff_position where post_id != 1
    order by post_id;
end; $$
delimiter ;

delimiter $$
create function position_getExists(postName varchar(15))
	returns int
begin
	declare temp int default -1;
    if exists (select postName from staff_position stp where upper(stp.postName)=upper(postname))then
		select 1 into temp;
	end if;
    return temp;
end; $$
delimiter ;


delimiter $$
create function position_get_sal(postName varchar(15))
	returns int unsigned
begin
	declare vbasic int unsigned default 0;
    select basic_sal into vbasic from staff_position where staff_position.postName=postName;
	return vbasic;
end; $$
delimiter ;

-- Table Staffs
create table Staff(
    staff_id int unsigned auto_increment,
    staffName varchar(50) not null,
	post_id int unsigned,
    DoB date not null,
    staffPhone varchar(10),
    staffState int unsigned default 1,
    bonus int unsigned not null default 0, -- %
    totalSal bigint unsigned not null default 0,
    constraint ck_staff_salary_bonus check(bonus >= 0),
	primary key(staff_id),
    constraint fk_staff_postid foreign key(post_id) references staff_position(post_id),
    constraint ck_staff_phone check(staffPhone regexp '^0[0-9]{9}$')
);
alter table staff auto_increment = 1000;
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Ho Xuan Phuong Dong", 2, "2001-10-20","0774839113", 1,10,8800000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Van An", 2, "1997-1-2","0123456789",1,7,8560000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Van D", 3, "1998-12-10","0213595384",1,6,9540000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Ngoc E", 3, "1997-2-14","0237482942",0,0,0);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Thi B", 3, "1992-3-20","0987654321",1,5,9450000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Thi C", 3, "1995-4-10","0123321456",0,0,0);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Ngoc K", 3, "1998-3-14","0584739264",1,5,9450000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Ngoc L", 4, "1996-7-21","0183749533",1,3,8240000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Mai M", 4, "1996-4-24","0483726483",1,0,8000000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Ngoc N", 4, "1997-6-11","0192345863",1,3,8240000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Thi O", 4, "2000-8-13","0392749572",1,0,8000000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Ngoc P", 4, "2000-5-24","0395836445",0,0,0);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Tran Q", 4, "2002-12-24","0384659364",1,0,8000000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Van J", 5, "1975-9-2","0384957364",1,0,6500000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Thi Ngoc F", 6, "1988-3-11","0384629465",1,0,6500000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Nguyen Thi G", 6, "1987-7-13","0485264693",1,0,6500000);
insert into staff(staffName, post_id, DoB, staffPhone,staffState,bonus,totalSal) values("Tran Van H", 5, "1988-4-24","0495739572",1,0,6500000);

delimiter $$
create procedure staff_new(in staffName varchar(50), in postname varchar(15), in DoB date, staffPhone varchar(10), in bonus int unsigned)
begin
	declare vpid varchar(15);
	
    declare salary int unsigned default 0;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    select post_id into vpid from staff_position stp where stp.postname=postname;
    select basic_sal+(basic_sal*bonus/100) into salary from staff_position stp where stp.post_id=vpid;
    
	set autocommit = 0;
	start transaction;
	insert into staff(staffName, post_id, DoB, staffPhone,bonus,totalSal)
	values(staffName, vpid, DoB, staffPhone,bonus,salary);
	commit;
	set autocommit = 1;
    
end; $$
delimiter ;

delimiter $$
create procedure staff_update(in staff_id int unsigned, in staffName varchar(50),
							in postname varchar(15), in DoB date, staffPhone varchar(10),
                            in bonus int unsigned)
begin
	declare vpid int unsigned default 0;
	
	declare salary int unsigned default 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    
	if exists (select st.staff_id from staff st where st.staff_id=staff_id and st.staffState=1) then
		/*select basic_sal into salary from staff_position stp join staff st on stp.post_id=st.post_id
        where st.staff_id=staff_id;*/
		select post_id into vpid from staff_position stp where stp.postname=postname;
		
        select basic_sal+(basic_sal*bonus/100) into salary from staff_position stp join staff st on stp.post_id=vpid
        where st.staff_id=staff_id;
		set autocommit = 0;
		start transaction;
		update staff st set st.staffName=staffName, st.post_id=vpid, st.DoB=DoB, st.staffPhone=staffPhone,
        st.bonus=bonus, st.totalSal=salary
        where st.staff_id=staff_id;
		commit;
		set autocommit = 1;
        
	end if;
end; $$
delimiter ;


delimiter $$
create procedure staff_delete(in staff_id int unsigned)
begin
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	if exists (select st.staff_id from staff st where st.staff_id=staff_id and st.staffState=1) then
		set autocommit = 0;
		start transaction;
		update staff set staffState=0,bonus=0,totalSal=0 where staff.staff_id=staff_id;
		commit;
		set autocommit = 1;
        
	end if;
end; $$
delimiter ;


delimiter $$
create procedure staff_list(in staffState int unsigned)
begin
	select st.staff_id, st.staffName, stp.postName, st.DoB, st.staffPhone,
    case when staffState=1
			then "Work"
		else "Retired"
		end	as State, st.bonus, st.totalSal
	from staff st join staff_position stp on st.post_id=stp.post_id
	where st.staffState=staffState
	order by st.staff_id asc;
end; $$
delimiter ;

delimiter $$
create procedure staff_search(in search varchar(50))
begin
	declare vsearch varchar(50) default null;
    declare vpost int default 0;
    select upper(search) into vsearch;
    select post_id into vpost from staff_position where upper(postName)=vsearch;
	select st.staff_id, st.staffName, stp.postName, st.DoB, st.staffPhone, 
	case when staffState=1
		then "Work"
	else "Retired"
	end	as State, st.bonus, st.totalSal
	from staff st join staff_position stp on st.post_id=stp.post_id
	where convert(st.staff_id,char) regexp vsearch
		or upper(st.staffName) regexp vsearch
        or stp.post_id regexp vpost
        or st.DoB regexp vsearch
        or st.staffPhone regexp vsearch
	order by st.staff_id asc;

end; $$
delimiter ;


delimiter $$
create procedure staff_get_info(in staff_id int unsigned)
begin
	select st.staff_id, st.staffName, stp.postName, st.DoB, st.staffPhone, 
        case when staffState=1
			then "Work"
		else "Retired"
		end	as State, st.Bonus, st.totalSal
        from staff st join staff_position stp on st.post_id=stp.post_id
        where st.staff_id=staff_id;
end; $$
delimiter ;

delimiter $$
create function staff_getExists(staff_id int unsigned)
	returns int
begin
	declare temp int default -1;
	select staff_id into temp from staff where staff.staff_id=staff_id;
	return temp;
end; $$
delimiter ;

-- -- ACCOUNT
create table account_type (
	type_id int unsigned,
    typeName varchar(15) not null,
    primary key (type_id)
);
insert into account_type values (1, "Admin");
insert into account_type values (2, "Cashier");

create table accounts(
	account_id int unsigned not null,
    username varchar(32) unique,
    passwd varchar(32) not null,
    type_id int unsigned,
    primary key (account_id),
    foreign key(type_id) references account_type(type_id)
);
insert into accounts values (1,"admin","0774839113", 1);
insert into accounts values (1000,"dong1000","dong1000", 2);
insert into accounts values (1001,"an1001","an1001", 2);

delimiter $$
create function account_get_valid(username varchar(32), passwd varchar(32))
	returns int
begin
	declare valid int default -1;
	if exists (select type_id from accounts where accounts.username=username and accounts.passwd=passwd) then
		select type_id into valid from accounts where accounts.username=username and accounts.passwd=passwd;
	end if;
    return valid;
end; $$
delimiter ;

delimiter $$
create function account_getExists(staff_id int unsigned)
	returns int
begin
	declare temp int default -1;
    select 1 into temp from accounts where accounts.account_id=staff_id;
	return temp;
end; $$
delimiter ;

delimiter $$
create function account_getExistsUsername(username varchar(32))
	returns int
begin
	declare temp int default -1;
    select 1 into temp from accounts where accounts.username=username;
	return temp;
end; $$
delimiter ;

delimiter $$
create procedure account_changePassword(in account_id int unsigned, in oldpasswd varchar(32), 
										in newpasswd varchar(32))
begin
	declare exit handler for sqlexception rollback;
	if exists(select username from accounts acc where acc.account_id=account_id and acc.passwd=oldpasswd) then
		set autocommit = 0;
        start transaction;
        update accounts set passwd=newpasswd where accounts.account_id=account_id;
        commit;
        set autocommit =1;
	end if;
end; $$
delimiter ;

delimiter $$
create procedure account_new(in staff_id int unsigned, in username varchar(32), in passwd varchar(32))
begin
	declare exit handler for sqlexception rollback;
	if not exists(select staff_id from accounts where account_id=staff_id) then
		set autocommit = 0;
		start transaction;
		insert into accounts values(staff_id, username, passwd, 2);
		commit;
		set autocommit = 1;
	end if;
end; $$
delimiter ;
use restaurant;

delimiter $$
create procedure account_list()
begin
	select account_id, username, passwd from accounts where type_id != 1;
end; $$
delimiter ;

-- --DISH
-- Table dish category
create table dish_category(
	cate_id int unsigned auto_increment,
    CateName varchar(50) not null unique,
    primary key(cate_id)
);
insert into dish_category(CateName) value("Gà/Vịt");
insert into dish_category(CateName) value("Bò/Heo");
insert into dish_category(CateName) value("Thủy/Hải sản");
insert into dish_category(CateName) value("Lẩu");
insert into dish_category(CateName) value("Cơm/Mì");
insert into dish_category(CateName) value("Soup");
insert into dish_category(CateName) value("Tráng miệng/Nước");
insert into dish_category(CateName) value("Khác");
use restaurant;
delimiter $$
create procedure list_category()
begin
	select CateName from dish_category order by cate_id;
end; $$
delimiter ;

delimiter $$
create procedure dish_cate_new(in CateName varchar(50))
begin
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    if not exists(select CateName from dish_category where upper(dish_category.CateName)=upper(CateName)) then
		set autocommit = 0;
		start transaction;
        insert into dish_category(CateName) value(CateName);
        commit;
        set autocommit = 1;
	end if;
end; $$
delimiter ;

create table dish_menu(
	dish_id int unsigned auto_increment,
    dishName varchar(50) not null,
    cate_id int unsigned,
    price int unsigned not null,
    unit varchar(15) not null,
	dishState int unsigned default 1,
    primary key(dish_id),
    constraint fk_dish_menu_cate_id foreign key(cate_id) references dish_category(cate_id),
    constraint ck_dish_menu_price check (price >= 0)
);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cá Chẽm Chiên Giòn", 3, 260000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cá Chẽm Hấp", 3, 260000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cá Dứa Chiên", 3, 100000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cá Bớp Chiên", 3, 100000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Lẩu Cá Chẽm", 4, 260000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cá Đuối Hấp", 3, 200000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cơm Chiên Hến", 5, 60000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cơm Chiên Cá Mặn", 5, 70000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cơm Chiên Hoàng Bào", 5, 70000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Cơm Chiên Dương Châu", 5, 50000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Mì Xào Bò", 5, 55000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Mì Xào GIòn", 5, 50000, "phần",0);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Mì Xào Hải Sản", 5, 60000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Lẩu Thái Hải Sản", 4, 150000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Lẩu Nấm", 4, 100000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Gà Luộc", 1, 160000, "con",0);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Gà Quay", 1, 160000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Gà Nướng Muối Ớt", 1, 170000, "con",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Bò Quanh Lửa Hồng", 2, 120000, "phần",0);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Bò Tái Chanh", 2, 100000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Soup Thập Cẩm ", 6, 50000, "phần",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Soup Cua", 6, 70000, "phần",0);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Nước Ngọt Các Loại", 7, 15000, "chai/lon",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Trái Cây Dĩa", 7, 40000, "dĩa",1);
insert into dish_menu(dishName, cate_id, price, unit,dishState) values("Bia", 7, 20000, "chai/lon",1);

delimiter $$
create procedure dish_new(in dishName varchar(50), in cateName varchar(50),
							in price int unsigned, in unit varchar(15))
begin
	declare vid int unsigned default 0;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    select cate_id into vid from dish_category dc where dc.cateName=cateName;
	set autocommit = 0;
	start transaction;
	insert into dish_menu(dishName, cate_id, price, unit) values(dishName,vid,price, unit);
	commit;
	set autocommit = 1;
    
end; $$
delimiter ;

delimiter $$
create procedure dish_get_info(in dish_id int unsigned)
begin
	select dish_id, dishName, cateName, price, unit,
    case when dishState=1
			then "Available"
		else "Deleted"
		end	as State
        from dish_menu dm join dish_category dc on dm.cate_id=dc.cate_id
        where dm.dish_id=dish_id;
end; $$
delimiter ;

delimiter $$
create procedure dish_list_by_cate(in CateName varchar(50))
begin
	declare cateid int unsigned default 0;
    select cate_id into cateid from dish_category where dish_category.cateName=CateName;
	if exists (select dish_id from dish_menu where dishState=1) then
		select dish_id, dishName, cateName, price, unit,
        case when dm.dishState=1
			then"Available"
		else "Deleted" end as state
        from dish_menu dm join dish_category dc on dm.cate_id=dc.cate_id where dm.cate_id=cateid
        and dm.dishState=1
		order by dish_id asc;
    end if;
end; $$
delimiter ;

delimiter $$
create procedure dish_list_deleted()
begin
	if exists (select dish_id from dish_menu where dishState=0) then
		select dish_id, dishName, cateName, price, unit, "Deleted" as state
        from dish_menu dm join dish_category dc on dm.cate_id=dc.cate_id
        where dishState=0
		order by dish_id asc;
    end if;
end; $$
delimiter ;

delimiter $$
create procedure dish_update(in dish_id int unsigned, in dishName varchar(50), in cateName varchar(50),
									in price int unsigned, in unit varchar(15))
begin
	declare vcate_id int;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    select cate_id into vcate_id from dish_category dc where dc.cateName=cateName;
	if exists (select dish_id from dish_menu dm where dm.dish_id=dish_id and dm.dishState=1) then
		set autocommit = 0;
		start transaction;
		update dish_menu dm set dm.dishName=dishName, dm.cate_id=vcate_id, dm.price=price, dm.unit=unit
        where dm.dish_id=dish_id;
		commit;
		set autocommit = 1;
        
	end if;
end; $$
delimiter ;


delimiter $$
create procedure dish_delete(in dish_id int unsigned)
begin
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	if exists (select dish_id from dish_menu where dish_menu.dish_id=dish_id and dish_menu.dishState=1) then
		set autocommit = 0;
		start transaction;
		update dish_menu set dishState=0 where dish_menu.dish_id=dish_id;
        delete from menu where menu.dish_id=dish_id;
		commit;
		set autocommit = 1;
        
	end if;
end; $$
delimiter ;


-- --TABLES
create table tables(
	table_id int unsigned auto_increment,
    tableName varchar(15) unique,
    num_of_seat int unsigned default 0,
    tableState int unsigned default 0,
    primary key(table_id)
);

insert into tables(tableName, num_of_seat) values("Mang Về", 0);
insert into tables(tableName, num_of_seat) values("Bàn 01", 4);
insert into tables(tableName, num_of_seat) values("Bàn 02", 4);
insert into tables(tableName, num_of_seat) values("Bàn 03", 4);
insert into tables(tableName, num_of_seat) values("Bàn 04", 4);
insert into tables(tableName, num_of_seat) values("Bàn 05", 10);
insert into tables(tableName, num_of_seat) values("Bàn 06", 10);
insert into tables(tableName, num_of_seat) values("Bàn 07", 4);
insert into tables(tableName, num_of_seat) values("Bàn 08", 4);
insert into tables(tableName, num_of_seat) values("Bàn 09", 4);
insert into tables(tableName, num_of_seat) values("Bàn 10", 4);
insert into tables(tableName, num_of_seat) values("Bàn 11", 4);
insert into tables(tableName, num_of_seat) values("Bàn 12", 10);
insert into tables(tableName, num_of_seat) values("Bàn 13", 10);
insert into tables(tableName, num_of_seat) values("Bàn 14", 4);
insert into tables(tableName, num_of_seat) values("Bàn 15", 4);
insert into tables(tableName, num_of_seat) values("Bàn 16", 4);
insert into tables(tableName, num_of_seat) values("Bàn 17", 4);
insert into tables(tableName, num_of_seat) values("Bàn 18", 4);
insert into tables(tableName, num_of_seat) values("Bàn 19", 4);
insert into tables(tableName, num_of_seat) values("Bàn 20", 4);
insert into tables(tableName, num_of_seat) values("Bàn 21", 4);

delimiter $$
create procedure table_new(in tableName varchar(15), in num_of_seat int unsigned)
begin
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    if not exists (select table_id from tables where tables.tableName=tableName) then
		set autocommit = 0;
		start transaction;
		insert into tables(tableName, num_of_seat) value(tableName,num_of_seat);
		commit;
		set autocommit = 1;
		
    end if;
end; $$
delimiter ;

delimiter $$
create procedure list_table_available()
begin
	select * from tables where tableState=0;
end; $$
delimiter ;

delimiter $$
create procedure table_list()
begin
	select * from tables;
end; $$
delimiter ;

-- --Invoice
create table Invoice (
	invoice_id bigint unsigned auto_increment,
    table_id int unsigned,
    staff_id int unsigned,
    invoiceDate datetime default current_timestamp,
    invoiceDateUpt datetime default current_timestamp,
    invoiceTotal bigint unsigned default 0,
    invoiceState int default 0,
    primary key(invoice_id),
    foreign key (table_id) references tables(table_id),
    foreign key (staff_id) references staff(staff_id),
    constraint ck_invoice_total check (invoiceTotal >= 0)
);

create table invoice_detail(
	invoice_id bigint unsigned,
    dish_id int unsigned,
    Quantity int ,
    foreign key (invoice_id) references invoice(invoice_id),
    foreign key (dish_id) references dish_menu(dish_id),
    primary key(invoice_id, dish_id),
    constraint ck_invoice_detail_quantity check(Quantity >= 0)
);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-21 10:10:10','2021-12-21 12:10:10',620000,1);
insert into invoice_detail values(1, 1, 1);
insert into invoice_detail values(1, 2, 1);
insert into invoice_detail values(1, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-21 12:12:12', '2021-12-21 14:12:12', 1300000,1);
insert into invoice_detail values(2,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-21 14:14:14', '2021-12-21 16:14:14',1180000,1);
insert into invoice_detail values(3,5,2);
insert into invoice_detail values(3,1,2);
insert into invoice_detail values(3,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-21 16:16:16', '2021-12-21 18:16:16',370000,1);
insert into invoice_detail values(4,7,2);
insert into invoice_detail values(4,11,2);
insert into invoice_detail values(4,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-21 20:20:20', '2021-12-21 22:20:20', 525000,1);
insert into invoice_detail values(5,3,3);
insert into invoice_detail values(5,11,3);
insert into invoice_detail values(5,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-21 21:11:11', '2021-12-21 23:11:11',1090000,1);
insert into invoice_detail values(6,4,1);
insert into invoice_detail values(6,8,3);
insert into invoice_detail values(6,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-22 10:10:10','2021-12-22 12:10:10',620000,1);
insert into invoice_detail values(7, 1, 1);
insert into invoice_detail values(7, 2, 1);
insert into invoice_detail values(7, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-22 12:12:12', '2021-12-22 14:12:12', 1300000,1);
insert into invoice_detail values(8,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-22 14:14:14', '2021-12-22 16:14:14',1180000,1);
insert into invoice_detail values(9,5,2);
insert into invoice_detail values(9,1,2);
insert into invoice_detail values(9,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-22 16:16:16', '2021-12-22 18:16:16',370000,1);
insert into invoice_detail values(10,7,2);
insert into invoice_detail values(10,11,2);
insert into invoice_detail values(10,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-22 20:20:20', '2021-12-22 22:20:20', 525000,1);
insert into invoice_detail values(11,3,3);
insert into invoice_detail values(11,11,3);
insert into invoice_detail values(11,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-22 21:11:11', '2021-12-22 23:11:11',1090000,1);
insert into invoice_detail values(12,4,1);
insert into invoice_detail values(12,8,3);
insert into invoice_detail values(12,1,3);


insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-23 10:10:10','2021-12-23 12:10:10',620000,1);
insert into invoice_detail values(13, 1, 1);
insert into invoice_detail values(13, 2, 1);
insert into invoice_detail values(13, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-23 12:12:12', '2021-12-23 14:12:12', 1300000,1);
insert into invoice_detail values(14,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-23 14:14:14', '2021-12-23 16:14:14',1180000,1);
insert into invoice_detail values(15,5,2);
insert into invoice_detail values(15,1,2);
insert into invoice_detail values(15,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-23 16:16:16', '2021-12-23 18:16:16',370000,1);
insert into invoice_detail values(16,7,2);
insert into invoice_detail values(16,11,2);
insert into invoice_detail values(16,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-23 20:20:20', '2021-12-23 22:20:20', 525000,1);
insert into invoice_detail values(17,3,3);
insert into invoice_detail values(17,11,3);
insert into invoice_detail values(17,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-23 21:11:11', '2021-12-23 23:11:11',1090000,1);
insert into invoice_detail values(18,4,1);
insert into invoice_detail values(18,8,3);
insert into invoice_detail values(18,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-24 10:10:10','2021-12-24 12:10:10',620000,1);
insert into invoice_detail values(19, 1, 1);
insert into invoice_detail values(19, 2, 1);
insert into invoice_detail values(19, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-24 12:12:12', '2021-12-24 14:12:12', 1300000,1);
insert into invoice_detail values(20,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-24 14:14:14', '2021-12-24 16:14:14',1180000,1);
insert into invoice_detail values(21,5,2);
insert into invoice_detail values(21,1,2);
insert into invoice_detail values(21,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-24 16:16:16', '2021-12-24 18:16:16',370000,1);
insert into invoice_detail values(22,7,2);
insert into invoice_detail values(22,11,2);
insert into invoice_detail values(22,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-24 20:20:20', '2021-12-24 22:20:20', 525000,1);
insert into invoice_detail values(23,3,3);
insert into invoice_detail values(23,11,3);
insert into invoice_detail values(23,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-24 21:11:11', '2021-12-24 23:11:11',1090000,1);
insert into invoice_detail values(24,4,1);
insert into invoice_detail values(24,8,3);
insert into invoice_detail values(24,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-25 10:10:10','2021-12-25 12:10:10',620000,1);
insert into invoice_detail values(25, 1, 1);
insert into invoice_detail values(25, 2, 1);
insert into invoice_detail values(25, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-25 12:12:12', '2021-12-25 14:12:12', 1300000,1);
insert into invoice_detail values(26,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-25 14:14:14', '2021-12-25 16:14:14',1180000,1);
insert into invoice_detail values(27,5,2);
insert into invoice_detail values(27,1,2);
insert into invoice_detail values(27,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-25 16:16:16', '2021-12-25 18:16:16',370000,1);
insert into invoice_detail values(28,7,2);
insert into invoice_detail values(28,11,2);
insert into invoice_detail values(28,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-25 20:20:20', '2021-12-25 22:20:20', 525000,1);
insert into invoice_detail values(29,3,3);
insert into invoice_detail values(29,11,3);
insert into invoice_detail values(29,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-25 21:11:11', '2021-12-25 23:11:11',1090000,1);
insert into invoice_detail values(30,4,1);
insert into invoice_detail values(30,8,3);
insert into invoice_detail values(30,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-26 10:10:10','2021-12-26 12:10:10',620000,1);
insert into invoice_detail values(31, 1, 1);
insert into invoice_detail values(31, 2, 1);
insert into invoice_detail values(31, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-26 12:12:12', '2021-12-26 14:12:12', 1300000,1);
insert into invoice_detail values(32,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-26 14:14:14', '2021-12-26 16:14:14',1180000,1);
insert into invoice_detail values(33,5,2);
insert into invoice_detail values(33,1,2);
insert into invoice_detail values(33,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-26 16:16:16', '2021-12-26 18:16:16',370000,1);
insert into invoice_detail values(34,7,2);
insert into invoice_detail values(34,11,2);
insert into invoice_detail values(34,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-26 20:20:20', '2021-12-26 22:20:20', 525000,1);
insert into invoice_detail values(35,3,3);
insert into invoice_detail values(35,11,3);
insert into invoice_detail values(35,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-26 21:11:11', '2021-12-26 23:11:11',1090000,1);
insert into invoice_detail values(36,4,1);
insert into invoice_detail values(36,8,3);
insert into invoice_detail values(36,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-27 10:10:10','2021-12-27 12:10:10',620000,1);
insert into invoice_detail values(37, 1, 1);
insert into invoice_detail values(37, 2, 1);
insert into invoice_detail values(37, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-27 12:12:12', '2021-12-27 14:12:12', 1300000,1);
insert into invoice_detail values(38,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-27 14:14:14', '2021-12-27 16:14:14',1180000,1);
insert into invoice_detail values(39,5,2);
insert into invoice_detail values(39,1,2);
insert into invoice_detail values(39,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-27 16:16:16', '2021-12-27 18:16:16',370000,1);
insert into invoice_detail values(40,7,2);
insert into invoice_detail values(40,11,2);
insert into invoice_detail values(40,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-27 20:20:20', '2021-12-27 22:20:20', 525000,1);
insert into invoice_detail values(41,3,3);
insert into invoice_detail values(41,11,3);
insert into invoice_detail values(41,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-27 21:11:11', '2021-12-27 23:11:11',1090000,1);
insert into invoice_detail values(42,4,1);
insert into invoice_detail values(42,8,3);
insert into invoice_detail values(42,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-28 10:10:10','2021-12-28 12:10:10',620000,1);
insert into invoice_detail values(43, 1, 1);
insert into invoice_detail values(43, 2, 1);
insert into invoice_detail values(43, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-28 12:12:12', '2021-12-28 14:12:12', 1300000,1);
insert into invoice_detail values(44,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-28 14:14:14', '2021-12-28 16:14:14',1180000,1);
insert into invoice_detail values(45,5,2);
insert into invoice_detail values(45,1,2);
insert into invoice_detail values(45,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-28 16:16:16', '2021-12-28 18:16:16',370000,1);
insert into invoice_detail values(46,7,2);
insert into invoice_detail values(46,11,2);
insert into invoice_detail values(46,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-28 20:20:20', '2021-12-28 22:20:20', 525000,1);
insert into invoice_detail values(47,3,3);
insert into invoice_detail values(47,11,3);
insert into invoice_detail values(47,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-28 21:11:11', '2021-12-28 23:11:11',1090000,1);
insert into invoice_detail values(48,4,1);
insert into invoice_detail values(48,8,3);
insert into invoice_detail values(48,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-29 10:10:10','2021-12-29 12:10:10',620000,1);
insert into invoice_detail values(49, 1, 1);
insert into invoice_detail values(49, 2, 1);
insert into invoice_detail values(49, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-29 12:12:12', '2021-12-29 14:12:12', 1300000,1);
insert into invoice_detail values(50,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-29 14:14:14', '2021-12-29 16:14:14',1180000,1);
insert into invoice_detail values(51,5,2);
insert into invoice_detail values(51,1,2);
insert into invoice_detail values(51,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-29 16:16:16', '2021-12-29 18:16:16',370000,1);
insert into invoice_detail values(52,7,2);
insert into invoice_detail values(52,11,2);
insert into invoice_detail values(52,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-29 20:20:20', '2021-12-29 22:20:20', 525000,1);
insert into invoice_detail values(53,3,3);
insert into invoice_detail values(53,11,3);
insert into invoice_detail values(53,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2021-12-29 21:11:11', '2021-12-29 23:11:11',1090000,1);
insert into invoice_detail values(54,4,1);
insert into invoice_detail values(54,8,3);
insert into invoice_detail values(54,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2021-12-30 10:10:10','2021-12-30 12:10:10',620000,1);
insert into invoice_detail values(55, 1, 1);
insert into invoice_detail values(55, 2, 1);
insert into invoice_detail values(55, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2021-12-30 12:12:12', '2021-12-30 14:12:12', 1300000,1);
insert into invoice_detail values(56,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2021-12-30 14:14:14', '2021-12-30 16:14:14',1180000,1);
insert into invoice_detail values(57,5,2);
insert into invoice_detail values(57,1,2);
insert into invoice_detail values(57,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2021-12-30 16:16:16', '2021-12-30 18:16:16',370000,1);
insert into invoice_detail values(58,7,2);
insert into invoice_detail values(58,11,2);
insert into invoice_detail values(58,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2021-12-30 20:20:20', '2021-12-30 22:20:20', 525000,1);
insert into invoice_detail values(59,3,3);
insert into invoice_detail values(59,11,3);
insert into invoice_detail values(59,21,1);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2022-01-01 07:10:10','2022-01-01 08:10:10',620000,1);
insert into invoice_detail values(60, 1, 1);
insert into invoice_detail values(60, 2, 1);
insert into invoice_detail values(60, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2022-01-01 08:12:12', '2022-01-01 09:12:12', 1300000,1);
insert into invoice_detail values(61,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2022-01-01 09:14:14', '2022-01-01 10:14:14',1180000,1);
insert into invoice_detail values(62,5,2);
insert into invoice_detail values(62,1,2);
insert into invoice_detail values(62,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2022-01-01 10:16:16', '2022-01-01 11:16:16',370000,1);
insert into invoice_detail values(63,7,2);
insert into invoice_detail values(63,11,2);
insert into invoice_detail values(63,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2022-01-01 11:20:20', '2022-01-01 12:20:20', 525000,1);
insert into invoice_detail values(64,3,3);
insert into invoice_detail values(64,11,3);
insert into invoice_detail values(64,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2022-01-01 12:11:11', '2022-01-01 13:11:11',1090000,1);
insert into invoice_detail values(65,4,1);
insert into invoice_detail values(65,8,3);
insert into invoice_detail values(65,1,3);

insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(2, 1000, '2022-01-01 13:10:10','2022-01-01 14:10:10',620000,1);
insert into invoice_detail values(66, 1, 1);
insert into invoice_detail values(66, 2, 1);
insert into invoice_detail values(66, 3, 1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(3, 1001, '2022-01-01 14:12:12', '2022-01-01 15:12:12', 1300000,1);
insert into invoice_detail values(67,2,5);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(5, 1001, '2022-01-01 15:14:14', '2022-01-01 16:14:14',1180000,1);
insert into invoice_detail values(68,5,2);
insert into invoice_detail values(68,1,2);
insert into invoice_detail values(68,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(15, 1000, '2022-01-01 16:16:16', '2022-01-01 17:16:16',370000,1);
insert into invoice_detail values(69,7,2);
insert into invoice_detail values(69,11,2);
insert into invoice_detail values(69,9,2);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(6, 1000, '2022-01-01 17:20:20', '2022-01-01 18:20:20', 525000,1);
insert into invoice_detail values(70,3,3);
insert into invoice_detail values(70,11,3);
insert into invoice_detail values(70,21,1);
insert into invoice(table_id,staff_id,invoiceDate,invoiceDateUpt, invoiceTotal,invoiceState) values(7, 1001, '2022-01-01 18:11:11', '2022-01-01 19:11:11',1090000,1);
insert into invoice_detail values(71,4,1);
insert into invoice_detail values(71,8,3);
insert into invoice_detail values(71,1,3);

delimiter $$
create procedure invoice_list()
begin
	select iv.invoice_id,tableName,concat(staffName," ",iv.staff_id) as staff ,iv.invoiceDate,iv.invoiceDateUpt,iv.invoiceTotal,
    case iv.invoiceState when 0 then "Unpaid"
    when 1 then "Paid"
    end as state
	from invoice iv join tables tb on iv.table_id=tb.table_id
    join staff st on iv.staff_id=st.staff_id
    order by invoiceState asc , invoice_id desc;
end; $$
delimiter ;

/*
delimiter $$
create procedure invoice_list2(in staff_id int unsigned)
begin
	select iv.invoice_id,tableName,concat(staffName," ",iv.staff_id) as staff ,iv.invoiceDate,iv.invoiceDateUpt,iv.invoiceTotal,
    case iv.invoiceState when 0 then "Unpaid"
    when 1 then "Paid"
    end as state
	from invoice iv join tables tb on iv.table_id=tb.table_id
    join staff st on iv.staff_id=st.staff_id
    where iv.staff_id=1000
    order by invoice_id desc,state asc
    ;
end; $$
delimiter ;
*/

delimiter $$
create function table_getValid(tableName varchar(15))
	returns int
begin
	declare tid int default 0;
    declare temp int default -1;
    select table_id into tid from tables where tables.tableName=tableName;
    if not exists(select table_id from tables where tables.table_id=tid and tables.tableState=1) then
		select 1 into temp;
	end if;
    if exists(select table_id from tables where tables.table_id=tid and tid=1) then
		select 1 into temp;
	end if;
    return temp;
end; $$
delimiter ; 

delimiter $$
create procedure invoice_get_info(in invoice_id int)
begin
	select iv.invoice_id,tableName,concat(staffName," ",iv.staff_id) as staff ,iv.invoiceDate,iv.invoiceDateUpt,iv.invoiceTotal,
    case iv.invoiceState when 0 then "Unpaid"
    when 1 then "Paid"
    end as state
	from invoice iv join tables tb on iv.table_id=tb.table_id
    join staff st on iv.staff_id=st.staff_id
    where iv.invoice_id=invoice_id;
end; $$
delimiter ;

delimiter $$
create procedure invoice_new(in tableName varchar(15), in staff_id int unsigned)
begin
	declare tid int default 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    select table_id into tid from tables where tables.tableName=tableName;
    set autocommit = 0;
	start transaction;
	insert into Invoice(table_id, staff_id, invoiceTotal) values (tid, staff_id, 0);
    update tables set tableState=1 where tables.table_id=tid;
    if exists(select table_id from tables where tables.table_id=tid and tid=1) then
		update tables set tableState=0 where tables.table_id=tid;
	end if;
	commit;
	set autocommit = 1;
end; $$
delimiter ;

delimiter $$
create procedure detail_update(in invoice_id bigint unsigned, in dish_id int unsigned, 
								in Quantity int )
begin
    declare giasp int;
    declare sl_them int;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    select price into giasp from dish_menu where dish_menu.dish_id=dish_id;
    set autocommit = 0;
	start transaction;
	if not exists (select dish_id from invoice_detail 
    where invoice_detail.invoice_id=invoice_id and invoice_detail.dish_id=dish_id) then
		insert into invoice_detail values(invoice_id, dish_id, Quantity);
        update invoice set invoiceTotal = invoiceTotal + Quantity * giasp , invoiceDateUpt=now()
        where invoice.invoice_id=invoice_id;
	else
		select Quantity - invoice_detail.Quantity into sl_them from invoice_detail
        where invoice_detail.invoice_id=invoice_id and invoice_detail.dish_id=dish_id;
        
        update invoice set invoiceTotal = invoiceTotal +  sl_them * giasp,invoiceDateUpt=now()
        where invoice.invoice_id=invoice_id;
        update invoice_detail set invoice_detail.Quantity=Quantity 
        where invoice_detail.invoice_id=invoice_id and invoice_detail.dish_id=dish_id;
	end if;
    if Quantity = 0 then
		delete from invoice_detail where invoice_detail.invoice_id=invoice_id and invoice_detail.dish_id=dish_id;
	end if;
    commit;
	set autocommit = 1;
	
end; $$
delimiter ;



delimiter $$
create procedure invoice_close(in invoice_id bigint unsigned)
begin
	declare vtableid int default 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	if exists (select invoice_id from invoice
    where invoice.invoice_id=invoice_id and invoice.invoiceState=0) then
		set autocommit = 0;
		start transaction;
        select table_id into vtableid from invoice where invoice.invoice_id=invoice_id;
        update tables set tableState=0 where table_id=vtableid;
		update invoice set invoiceState=1, invoiceDateUpt=now() where invoice.invoice_id=invoice_id;
		commit;
		set autocommit = 1;
		
	end if;
end; $$
delimiter ;

delimiter $$
create procedure invoice_list_detail(in invoice_id bigint unsigned)
begin
	select @row := @row + 1 as "No.",dm.dishName,ivd.dish_id,dm.price as "unit_price",quantity,
    dm.price*quantity as amount
    from invoice_detail ivd join dish_menu dm on ivd.dish_id=dm.dish_id, (select @row :=0) r
    where ivd.invoice_id=invoice_id;
end; $$
delimiter ;

delimiter $$
create function invoice_getPaid(invoice_id int)
	returns int
begin
	declare temp int default -1;
    select iv.invoiceState into temp from invoice iv where iv.invoice_id=invoice_id;
    return temp;
end; $$
delimiter ;


/*
delimiter $$
create function invoice_statisticDate(invoiceDateUpt date)
	returns bigint unsigned
begin
	declare temp bigint unsigned default 0;
    if exists(select iv.invoiceDateUpt from invoice iv where date(iv.invoiceDateUpt)=invoiceDateUpt) then
		select sum(iv.invoiceTotal) into temp from invoice iv where date(iv.invoiceDateUpt) = invoiceDateUpt;
	end if;
	return temp;
end; $$
delimiter ;



delimiter $$
create function invoice_statisticYear(yeardate varchar(20))
	returns bigint unsigned
begin
	declare temp bigint unsigned default 0;
    if exists(select iv.invoiceDateUpt from invoice iv
    where year(iv.invoiceDateUpt) = yeardate) then
		select sum(iv.invoiceTotal) into temp from invoice iv 
		where year(iv.invoiceDateUpt) = yeardate;
	end if;
	return temp;
end; $$
delimiter ;
*/

/*
delimiter $$
create procedure statisticByYear(in iyear varchar(10))
begin
	select month(invoiceDateUpt) as iMonth, count(invoice_id) as NumInvoice,sum(invoiceTotal) as iTotal from invoice
	where year(invoiceDateUpt)=iyear
	group by iMonth;
end; $$
delimiter ;
*/
/*
select date(invoiceDateUpt) as iDay, count(invoice_id) as NumInvoice,sum(invoiceTotal) as iTotal from invoice

group by iDay
;*/
delimiter $$
create function invoice_statisticMonth(monthdate varchar(20), yeardate varchar(20))
	returns bigint unsigned
begin
	declare temp bigint unsigned default 0;
    if exists(select iv.invoiceDateUpt from invoice iv 
    where month(iv.invoiceDateUpt) = monthdate and year(iv.invoiceDateUpt) = yeardate) then
		select sum(iv.invoiceTotal) into temp from invoice iv 
		where month(iv.invoiceDateUpt) = monthdate and year(iv.invoiceDateUpt) = yeardate;
	end if;
	return temp;
end; $$
delimiter ;

delimiter $$
create function invoice_statisticNumByMonth(monthdate varchar(20), yeardate varchar(20))
	returns int unsigned
begin
	declare temp bigint unsigned default 0;
    if exists(select iv.invoiceDateUpt from invoice iv 
    where month(iv.invoiceDateUpt) = monthdate and year(iv.invoiceDateUpt) = yeardate) then
		select count(invoice_id) into temp from invoice iv 
		where month(iv.invoiceDateUpt) = monthdate and year(iv.invoiceDateUpt) = yeardate;
	end if;
	return temp;
end; $$
delimiter ;

delimiter $$
create procedure statisticByMonth(in iMonth varchar(10), in iYear varchar(10))
begin
	select date(invoiceDateUpt) as iDay, count(invoice_id) as NumInvoice,sum(invoiceTotal) as iTotal from invoice
	where month(invoiceDateUpt)=iMonth and year(invoiceDateUpt)=iYear
    group by iDay;
end; $$
delimiter ;

/*
call statisticByMonth("12","2021");
select invoice_statisticMonth("12","2021");
select invoice_statisticNumByMonth("12","2021");
*/

create table dayweek(
	day_id int,
    dayEng varchar(10),
    dayViet varchar(10),
    primary key (day_id)
);
insert into dayweek value (1, "Monday", "Thứ Hai");
insert into dayweek value (2, "Tuesday", "Thứ Ba");
insert into dayweek value (3, "Wednesday", "Thứ Tư");
insert into dayweek value (4, "Thursday", "Thứ Năm");
insert into dayweek value (5, "Friday", "Thứ Sáu");
insert into dayweek value (6, "Saturday", "Thứ Bảy");
insert into dayweek value (7, "Sunday", "Chủ Nhật");

create table menu(
	day_id int,
	dish_id int unsigned,
    foreign key (day_id) references dayweek(day_id),
    foreign key (dish_id) references dish_menu(dish_id),
    primary key(day_id,dish_id)
);

delimiter $$
create procedure menu_add(in dayEng varchar(10), dish_id int unsigned)
begin
	declare vdayid int default 0;
	declare exit  handler for sqlexception rollback;
    select day_id into vdayid from dayweek where dayweek.dayEng=dayEng;
	if not exists(select mn.day_id from menu mn where mn.day_id=vdayid and mn.dish_id=dish_id) then
		set autocommit = 0;
        start transaction;
        insert into menu values(vdayid, dish_id);
        commit;
        set autocommit = 1;
	end if;
end; $$
delimiter ;
select * from dish_menu;
select * from menu;
insert into menu values(1,23);
insert into menu values(2,23);
insert into menu values(3,23);
insert into menu values(4,23);
insert into menu values(5,23);
insert into menu values(6,23);
insert into menu values(7,23);
insert into menu values(1,24);
insert into menu values(2,24);
insert into menu values(3,24);
insert into menu values(4,24);
insert into menu values(5,24);
insert into menu values(6,24);
insert into menu values(7,24);
insert into menu values(1,25);
insert into menu values(2,25);
insert into menu values(3,25);
insert into menu values(4,25);
insert into menu values(5,25);
insert into menu values(6,25);
insert into menu values(7,25);

insert into menu values(1,1);
insert into menu values(3,1);
insert into menu values(5,1);
insert into menu values(7,1);
insert into menu values(2,2);
insert into menu values(4,2);
insert into menu values(6,2);
insert into menu values(1,3);
insert into menu values(3,3);
insert into menu values(5,3);
insert into menu values(7,3);
insert into menu values(2,4);
insert into menu values(4,4);
insert into menu values(6,4);
insert into menu values(7,4);
insert into menu values(1,5);
insert into menu values(3,5);
insert into menu values(5,5);
insert into menu values(2,6);
insert into menu values(4,6);
insert into menu values(6,6);
insert into menu values(1,7);
insert into menu values(5,7);
insert into menu values(1,8);
insert into menu values(5,8);
insert into menu values(7,8);
insert into menu values(2,9);
insert into menu values(4,9);
insert into menu values(6,9);
insert into menu values(7,9);
insert into menu values(1,10);
insert into menu values(2,10);
insert into menu values(3,10);
insert into menu values(4,11);
insert into menu values(5,11);
insert into menu values(6,11);
insert into menu values(7,11);
insert into menu values(1,12);
insert into menu values(2,12);
insert into menu values(5,12);
insert into menu values(6,12);
insert into menu values(1,13);
insert into menu values(3,13);
insert into menu values(5,13);
insert into menu values(7,13);
insert into menu values(2,14);
insert into menu values(4,14);
insert into menu values(6,14);
insert into menu values(2,15);
insert into menu values(3,15);
insert into menu values(5,15);
insert into menu values(6,15);
insert into menu values(1,16);
insert into menu values(2,16);
insert into menu values(3,16);
insert into menu values(4,16);
insert into menu values(5,16);
insert into menu values(6,16);
insert into menu values(7,16);
insert into menu values(5,17);
insert into menu values(6,17);
insert into menu values(7,17);
insert into menu values(1,18);
insert into menu values(2,18);
insert into menu values(3,18);
insert into menu values(4,19);
insert into menu values(1,20);
insert into menu values(4,20);
insert into menu values(1,21);
insert into menu values(2,21);
insert into menu values(3,21);
insert into menu values(4,21);
insert into menu values(5,21);
insert into menu values(6,21);
insert into menu values(7,21);
insert into menu values(1,22);
insert into menu values(2,22);
insert into menu values(3,22);
insert into menu values(4,22);
insert into menu values(5,22);
insert into menu values(6,22);
insert into menu values(7,22);

delimiter $$
create function dish_get_max()
	returns int
begin
	declare temp int default 0;
	select max(dish_id) into temp from dish_menu;
	return temp;
end; $$
delimiter ;


delimiter $$
create procedure menu_delete(in dayEng varchar(10), dish_id int unsigned)
begin
	declare vdayid int default 0;
	declare exit  handler for sqlexception rollback;
    select day_id into vdayid from dayweek where dayweek.dayEng=dayEng;
	if exists(select mn.day_id from menu mn where mn.day_id=vdayid and mn.dish_id=dish_id) then
		set autocommit = 0;
        start transaction;
        delete from menu where menu.day_id=vdayid and menu.dish_id=dish_id;
        commit;
        set autocommit = 1;
	end if;
end; $$
delimiter ;

delimiter $$
create function menu_getExists(dayEng varchar(10), dish_id int)
	returns int
begin
	declare temp int default 0;
    select 1 into temp from menu mn join dayweek dw on mn.day_id=dw.day_id
    where dw.dayEng=dayEng and mn.dish_id=dish_id;
    return temp;
end; $$
delimiter ;

delimiter $$
create function menu_getMenuOf(dish_id int)
	returns varchar(80)
begin
	declare temp varchar(80) default "";
    declare is_done int default 0;
    declare vday varchar(80) default "";
    
    DECLARE cday CURSOR FOR select dayEng from dayweek dw join menu mn on dw.day_id=mn.day_id
	where mn.dish_id=dish_id;
    declare continue handler for not found set is_done = 1;
	open cday;
	get_list: loop
		fetch cday into vday;
		if is_done =1 then
			leave get_list;
		end if;
		select concat(temp," ", vday) into temp;
        
 	end loop get_list;
	close cday;
    return temp;
end; $$
delimiter ;

delimiter $$
create procedure invoice_search(in search varchar(50))
begin
	declare vsearch varchar(50) default null;
    select upper(search) into vsearch;
    
    select iv.invoice_id,tableName,concat(staffName," ",iv.staff_id) as staff ,iv.invoiceDate,iv.invoiceDateUpt,iv.invoiceTotal,
    case iv.invoiceState when 0 then "Unpaid"
    when 1 then "Paid"
    end as state
	from invoice iv join tables tb on iv.table_id=tb.table_id
    join staff st on iv.staff_id=st.staff_id
	where convert(iv.invoice_id,char) regexp vsearch
		or upper(tableName) regexp vsearch
        or invoiceDate regexp vsearch
        or invoiceDateUpt regexp vsearch
	order by iv.invoice_id desc;
end; $$
delimiter ;


