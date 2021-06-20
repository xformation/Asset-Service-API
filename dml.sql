select * from organization
select * from accounts

--insert into organization (id,name,description,status,created_on,updated_on,updated_by,created_by) values (1, 'Synectiks', 'Synectiks pvt ltd','ACTIVE',current_date,current_date,'Application','Application')
--update accounts set organization_id = 1, created_on = current_date - 10
--update accounts set status = 'DEACTIVE'

select * from organizational_unit

--insert into organizational_unit (id,name,description,status,created_on,updated_on,updated_by,created_by,organization_id) values (1,'HR','HR department','ACTIVE',current_date,current_date,'Application','Application',1);
--insert into organizational_unit (id,name,description,status,created_on,updated_on,updated_by,created_by,organization_id) values (2,'Finance','Finance department','ACTIVE',current_date,current_date,'Application','Application',1);
--insert into organizational_unit (id,name,description,status,created_on,updated_on,updated_by,created_by,organization_id) values (3,'Networking','Networking department','ACTIVE',current_date,current_date,'Application','Application',1);

--insert into accounts (id,name, description,account_id,tenant_id, access_key,secret_key,region, bucket,cloud_type,status, created_on,updated_on,updated_by,created_by,organization_id,organizational_unit_id), 
--values (2,'Manoj','AWS cloud','002','657907747545','AKIAZSLS3RLMTRT5NJF4','y+FNh1i30ULBkcaEjQmVwj8YV2ByjtaZJpcLxF1O','us-east-1','xformation.synectiks.com','AWS','ACTIVE',current_timestamp,current_timestamp,'Application','Application',1,1);
--
--insert into accounts (id,name, description,account_id,tenant_id, access_key,secret_key,region, bucket,cloud_type,status, created_on,updated_on,updated_by,created_by,organization_id,organizational_unit_id), 
--values (3,'Manoj','AWS cloud','003','657907747545','AKIAZSLS3RLMTRT5NJF4','y+FNh1i30ULBkcaEjQmVwj8YV2ByjtaZJpcLxF1O','us-east-1','xformation.synectiks.com','AWS','ACTIVE',current_timestamp,current_timestamp,'Application','Application',1,2);
--
--insert into accounts (id,name, description,account_id,tenant_id, access_key,secret_key,region, bucket,cloud_type,status, created_on,updated_on,updated_by,created_by,organization_id,organizational_unit_id), 
--values (4,'Manoj','AWS cloud','004','657907747545','AKIAZSLS3RLMTRT5NJF4','y+FNh1i30ULBkcaEjQmVwj8YV2ByjtaZJpcLxF1O','us-east-1','xformation.synectiks.com','AWS','ACTIVE',current_timestamp,current_timestamp,'Application','Application',1,3)
--

select * from cloud_asset
--insert into cloud_asset(id,account_id,type,name,description,status,created_on,updated_on,updated_by,created_by) values (1,'001','AWS','VPC','VPC Asset','ACTIVE',current_date,current_date,'Application','Application');
--insert into cloud_asset(id,account_id,type,name,description,status,created_on,updated_on,updated_by,created_by) values (2,'002','AWS','VPN','VPN Asset','ACTIVE',current_date,current_date,'Application','Application');
--insert into cloud_asset(id,account_id,type,name,description,status,created_on,updated_on,updated_by,created_by) values (3,'002','AWS','EC2','EC2 Asset','ACTIVE',current_date,current_date,'Application','Application');
--insert into cloud_asset(id,account_id,type,name,description,status,created_on,updated_on,updated_by,created_by) values (4,'003','AWS','RDS','RDS Asset','ACTIVE',current_date,current_date,'Application','Application');

select * from application_assets
