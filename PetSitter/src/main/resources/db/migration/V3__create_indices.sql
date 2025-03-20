create index if not exists login_idx on users (login);

create index if not exists user_id_sitter_idx on sitter(user_id);
create index if not exists user_id_customer_idx on customer(user_id, available);

create index if not exists sitter_customer_id_review_idx on review(sitter_id, customer_id);

create index if not exists sitter_id_review_idx on review(sitter_id);

