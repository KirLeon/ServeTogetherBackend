CREATE TABLE IF NOT EXISTS "market_items"
(
    "id"       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"    TEXT NOT NULL,
    "price"    INT  NOT NULL,
    "img_path" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "account_info"
(
    "id"             BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "role"           TEXT NOT NULL,
    "username"       TEXT NOT NULL UNIQUE,
    "password"       TEXT NOT NULL,
    "phone_number"   TEXT NOT NULL UNIQUE,
    "registry_token" TEXT NULL
);

CREATE TABLE IF NOT EXISTS "volunteers"
(
    "uid"        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "info_id"    BIGINT REFERENCES "account_info" ("id"),
    "group_name" TEXT,
    "coins"      INT NOT NULL
);

CREATE TABLE IF NOT EXISTS "volunteer_groups"
(
    "group_name"                     TEXT PRIMARY KEY,
    "active_announcements_quantity"  INTEGER NOT NULL,
    "pending_announcements_quantity" INTEGER NOT NULL
);

ALTER TABLE "volunteers"
    ADD FOREIGN KEY ("group_name") REFERENCES "volunteer_groups" (group_name);

CREATE TABLE IF NOT EXISTS "administrators"
(
    "uid"     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "info_id" BIGINT REFERENCES "account_info" ("id")
);

CREATE TABLE IF NOT EXISTS "announcements"
(
    "id"               BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"            TEXT   NOT NULL,
    "status"           TEXT   NOT NULL,
    "content"          TEXT   NOT NULL,
    "reward"           BIGINT NOT NULL,
    "creation_date"    DATE DEFAULT current_date,
    "expiration_date"  DATE DEFAULT current_date + INTERVAL '5 days',
    "owner"            BIGINT NOT NULL,
    "volunteer_groups" TEXT   NULL
);

CREATE TABLE IF NOT EXISTS "invite_keys"
(
    "code"      TEXT PRIMARY KEY,
    "role"      TEXT NOT NULL,
    "activated" BOOLEAN DEFAULT FALSE
);

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("owner") REFERENCES "administrators" ("uid");

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("volunteer_groups") REFERENCES "volunteer_groups" ("group_name");

CREATE INDEX idx_announcements_title ON announcements ("title");
CREATE INDEX idx_account_info_username ON account_info ("username");
