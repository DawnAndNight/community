"# community" 


#脚本
```sql
CREATE CACHED TABLE "PUBLIC"."USER"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_4FC61F1F_5397_42A0_BE67_F944DBE890F8" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_4FC61F1F_5397_42A0_BE67_F944DBE890F8",
    "ACCOUNT_ID" VARCHAR(100),
    "NAME" VARCHAR(50),
    "TOKEN" CHAR(36),
    "GMT_CREATE" BIGINT,
    "GMT_MODIFIED" BIGINT
)


```
mvn flyway:migrate\
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
