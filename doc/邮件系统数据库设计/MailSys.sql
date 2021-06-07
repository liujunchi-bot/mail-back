/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020-03-31 11:37:44                          */
/*==============================================================*/


drop table if exists Attachment;

drop table if exists Contact;

drop table if exists File;

drop table if exists Mail;

drop table if exists Parameter;

drop table if exists User;

/*==============================================================*/
/* Table: Attachment                                            */
/*==============================================================*/
create table Attachment
(
   mail_id              int not null,
   attach_order         int not null,
   file_id              int not null,
   primary key (mail_id, attach_order)
);

/*==============================================================*/
/* Table: Contact                                               */
/*==============================================================*/
create table Contact
(
   con_id               int not null,
   host_account         varchar(50) not null,
   friend__account      varchar(50) not null,
   primary key (con_id)
);

alter table Contact comment 'ͨѶ¼';

/*==============================================================*/
/* Table: File                                                  */
/*==============================================================*/
create table File
(
   file_path            varchar(200),
   file_name            varchar(100),
   file_id              int not null,
   primary key (file_id)
);

/*==============================================================*/
/* Table: Mail                                                  */
/*==============================================================*/
create table Mail
(
   mail_id              int not null,
   sender_account       varchar(50) not null,
   receiver_account     varchar(50) not null,
   date_time            datetime not null,
   subject              varchar(100) not null,
   content              varchar(1000),
   mail_state           int not null,
   size                 int,
   primary key (mail_id)
);

/*==============================================================*/
/* Table: Parameter                                             */
/*==============================================================*/
create table Parameter
(
   IP_filtering_rule    varchar(100),
   mail_filtering_rule  varchar(100),
   SMTP_state           int not null,
   SMTP_port            int not null default 25,
   POP3_state           int not null,
   POP3_port            int not null default 110,
   domain_name          varchar(20) not null default 'test.com'
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   nickname             varchar(20),
   account              varchar(50) not null,
   password             varchar(20) not null,
   role                 int default 1,
   primary key (account)
);

alter table Attachment add constraint FK_attach foreign key (file_id)
      references File (file_id) on delete restrict on update restrict;

alter table Attachment add constraint FK_contain foreign key (mail_id)
      references Mail (mail_id) on delete restrict on update restrict;

alter table Contact add constraint FK_friend_account foreign key (friend__account)
      references User (account) on delete restrict on update restrict;

alter table Contact add constraint FK_host_account foreign key (host_account)
      references User (account) on delete restrict on update restrict;

alter table Mail add constraint FK_recever_account foreign key (receiver_account)
      references User (account) on delete restrict on update restrict;

alter table Mail add constraint FK_sender_account foreign key (sender_account)
      references User (account) on delete restrict on update restrict;

