alter table QUESTION alter column CREATOR BIGINT default NOT NULL ;
alter table COMMENT
    alter column COMMENTOR BIGINT default NOT NULL;