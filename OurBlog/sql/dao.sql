select title,content,type,release_date,blog_from,flag from tb_blog where user_id="";

select title,content,type,release_date,blog_from,flag from tb_blog where title="";

insert into tb_blog(user_id,title,content,type,release_date,blog_from,flag) values("","","","","","","");

delete from tb_blog where blog_id="";


select * from tb_user where user_name="";
insert into tb_user(user_name,user_pass);
update tb_user set user_name="",user_pass="",head_protrait="" where user_id="";
delete from tb_user where user_id=""; 


select * from tb_admin where admin_name="";
insert into tb_admin(admin_name,admin_pass) values("","");
update tb_admin set admin_name="",admin_pass="" where admin_id="";


select content,user_name,review_date from tb_comment com,tb_user usr where com.user_id=usr.user_id && blog_id="1";
select content,user_id,review_date from tb_comment where blog_id="1";
insert into tb_comment (blog_id,user_id,content,comment_date) values ("","","","")
delete from tb_comment where comment_id ="";


select content,user_id,review_date from tb_comment where blog_id="1";
insert into tb_comment (blog_id,user_id,content,comment_date) values ("","","","")
delete from tb_comment where comment_id ="";
