DROP TABLE IF EXISTS  BMSGroupMenu;
DROP TABLE IF EXISTS  BMSUserGroup;
DROP TABLE IF EXISTS  bmsgroupauth;
DROP TABLE IF EXISTS  BMSMenuAuth;
DROP TABLE IF EXISTS  BMSUser;
DROP TABLE IF EXISTS  BMSGroup;
DROP TABLE IF EXISTS  BMSMenu;
DROP TABLE IF EXISTS  BMSAuth;
DROP TABLE IF EXISTS  BMSAuthMapping;

CREATE TABLE BMSUser (
    ID int NOT NULL AUTO_INCREMENT,
    UserName varchar(50) NOT NULL,
    UserPass varchar(50) NOT NULL,
    UserType int NOT NULL,
    Email varchar(100) NOT NULL,
    Mobile varchar(50) NOT NULL,
    Phone varchar(50) NOT NULL,
    Description varchar(250) NOT NULL,
    Active bit NOT NULL,
    LogonTimes int NOT NULL,
    LogonIp varchar(20) NOT NULL,
    LastLogonTime datetime NULL,
    CreateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CreateUser varchar(50) NOT NULL,
    CreateUserID int NOT NULL,
    UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
    UNIQUE KEY UserName (UserName) USING BTREE
);

CREATE TABLE BMSGroup (
	ID int NOT NULL AUTO_INCREMENT,
	GroupName varchar(50) NOT NULL ,
	Description varchar(200) NULL ,
	CreateTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CreateUser  varchar(50) NOT NULL,
	CreateUserID int NOT NULL,
	UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
    UNIQUE KEY GroupName (GroupName) USING BTREE
);

CREATE TABLE BMSUserGroup (
	ID int NOT NULL AUTO_INCREMENT,
	UserID int NOT NULL ,
	GroupID int NOT NULL ,
	CreateTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CreateUser  varchar(50) NOT NULL,
	UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
    UNIQUE KEY UserGroup (UserID,GroupID) USING BTREE,
    CONSTRAINT BMSUsergroup_ibfk_2 FOREIGN KEY (GroupID) REFERENCES BMSGroup (ID) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT BMSUsergroup_ibfk_1 FOREIGN KEY (UserID) REFERENCES BMSUser (ID) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE BMSMenu (
	ID int NOT NULL AUTO_INCREMENT,
	MenuName varchar(50) NOT NULL,
	ParentID int NOT NULL ,
	Url  varchar(250) NOT NULL ,
	Sort int NOT NULL ,
	MenuType int NOT NULL DEFAULT'1',
	CreateTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CreateUser  varchar(50) NOT NULL ,
	UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
    UNIQUE KEY ID (ID) USING BTREE
);

CREATE TABLE BMSGroupMenu (
	ID int NOT NULL AUTO_INCREMENT,
	GroupID int NOT NULL ,
	MenuID int NOT NULL ,
	CreateTime  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CreateUser  varchar(50) NOT NULL,
	UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
    UNIQUE KEY GroupMenu (GroupID,MenuID) USING BTREE,
    CONSTRAINT BMSGroupmenu_ibfk_2 FOREIGN KEY (GroupID) REFERENCES BMSGroup (ID) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT BMSGroupmenu_ibfk_1 FOREIGN KEY (MenuID) REFERENCES BMSMenu (ID) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE BMSAuth (
  ID int(11) NOT NULL AUTO_INCREMENT,
  FunctionID varchar(50) NOT NULL,
  AuthName varchar(50) NOT NULL,
  Description varchar(50) NULL,
  Status bit NOT NULL,
  CreateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CreateUser varchar(50) NOT NULL,
  UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  UNIQUE KEY AUTH_ID (ID) USING BTREE,
  UNIQUE KEY AUTH_FUNC_ID (FunctionID) USING BTREE
);

CREATE TABLE bmsgroupauth (
  ID int(11) NOT NULL AUTO_INCREMENT,
  GroupID int(11) NOT NULL,
  AuthID int(11) NOT NULL,
  CreateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CreateUser varchar(50) NOT NULL,
  UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  UNIQUE KEY GroupAuth (GroupID, AuthID) USING BTREE,
  KEY AuthID (AuthID),
  CONSTRAINT bmsgroupauth_ibfk_2 FOREIGN KEY (AuthID) REFERENCES bmsauth (ID) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT bmsgroupauth_ibfk_1 FOREIGN KEY (GroupID) REFERENCES bmsgroup (ID) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE BMSMenuAuth (
  ID int(11) NOT NULL AUTO_INCREMENT,
  MenuID int(11) NOT NULL,
  AuthID int(11) NOT NULL,
  CreateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CreateUser varchar(255) NOT NULL,
  UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  UNIQUE KEY MenuAuth (MenuID, AuthID) USING BTREE,
  KEY bmsmenuauth_ibfk_1 (MenuID),
  CONSTRAINT bmsmenuauth_ibfk_2 FOREIGN KEY (AuthID) REFERENCES bmsauth (ID) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT bmsmenuauth_ibfk_1 FOREIGN KEY (MenuID) REFERENCES bmsmenu (ID) ON DELETE CASCADE ON UPDATE NO ACTION 
);

CREATE TABLE BMSAuthMapping (
  ID int(11) NOT NULL AUTO_INCREMENT,
  MenuID int(11) NOT NULL,
  AuthID int(11) NOT NULL,
  Url varchar(255) NOT NULL,
  Status bit NOT NULL,
  CreateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CreateUser varchar(255) NOT NULL, 
  UpdateTime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  UNIQUE KEY AuthMapping (MenuID, AuthID, Url) USING BTREE
);

/******************INIT*********************/
INSERT INTO BMSUser VALUES (1, 'admin', '21232F297A57A5A743894A0E4A801FC3',1,'14876534@qq.com', '13818668209', '021-11011011', '', 1,0,'127.0.0.1','1900-1-1',now(),'admin',1,now());
INSERT INTO BMSGroup VALUES (1, 'admin','administrator',now(),'admin',1,now());
INSERT INTO BMSUserGroup VALUES (1,1,1,	now(),'admin',now());

INSERT INTO BMSMenu VALUES (1, '系统设置',0, '',1,1,now(),'admin',now());
INSERT INTO BMSMenu VALUES (2, '权限配置',1, '/mgr/authset/',1,1,now(),'admin',now());
INSERT INTO BMSMenu VALUES (3, '菜单管理',1, '/mgr/menu/',2,1,now(),'admin',now());
INSERT INTO BMSMenu VALUES (4, '用户管理',1, '/mgr/user/',3,1,now(),'admin',now());
INSERT INTO BMSMenu VALUES (5, '分组管理',1, '/mgr/group/',4,1,now(),'admin',now());

INSERT INTO BMSGroupMenu VALUES(1,1,1,now(),'admin',now());
INSERT INTO BMSGroupMenu VALUES(2,1,2,now(),'admin',now());
INSERT INTO BMSGroupMenu VALUES(3,1,3,now(),'admin',now());
INSERT INTO BMSGroupMenu VALUES(4,1,4,now(),'admin',now());
INSERT INTO BMSGroupMenu VALUES(5,1,5,now(),'admin',now());

/*用户*/
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_VIEW','用户查看','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_ADD','用户增加','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_DELETE','用户删除','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_UPDATE','用户更新','',1,'admin');
/*分组*/
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('GROUP_VIEW','分组查看','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('GROUP_ADD','分组增加','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('GROUP_DELETE','分组删除','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('GROUP_UPDATE','分组更新','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('GROUP_MENU','分组菜单管理','',1,'admin');
/*用户分组映射*/
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_GROUP_VIEW','分组用户查看','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_GROUP_ADD','分组用户增加','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_GROUP_DELETE','分组用户删除','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_GROUP_GROUPS','用户分组查看','',1,'admin');
INSERT INTO bmsauth(FunctionID, AuthName,Description,Status,CreateUser) VALUES('USER_GROUP_BIND','用户分组更新','',1,'admin');

INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(4, 1,'/mgr/user/view',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(4, 2,'/mgr/user/add',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(4, 3,'/mgr/user/update',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(4, 4,'/mgr/user/delete',1,'admin');

INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 5,'/mgr/group/view',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 6,'/mgr/group/add',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 7,'/mgr/group/update',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 8,'/mgr/group/delete',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 9,'/mgr/groupmenu',1,'admin');

INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 10,'/mgr/usergroup/view',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 11,'/mgr/usergroup/add',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 12,'/mgr/usergroup/delete',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 13,'/mgr/usergroup/groups/view',1,'admin');
INSERT INTO bmsauthmapping(MenuID,AuthID,Url,Status,CreateUser) VALUES(5, 14,'/mgr/usergroup/groups/bind',1,'admin');
