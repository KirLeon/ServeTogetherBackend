CREATE TABLE "market_items"
(
    "id"       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"    TEXT   NOT NULL,
    "price"    BIGINT NOT NULL,
    "img_path" TEXT   NOT NULL
);

CREATE TABLE "volunteers"
(
    "username"     TEXT PRIMARY KEY,
    "password"     TEXT   NOT NULL,
    "phone_number" TEXT   NOT NULL UNIQUE,
    "group_name"   TEXT,
    "coins"        BIGINT NOT NULL
);

CREATE TABLE "volunteer_groups"
(
    "group_name"                     TEXT PRIMARY KEY,
    "active_announcements_quantity"  INTEGER NOT NULL,
    "pending_announcements_quantity" INTEGER NOT NULL
);

ALTER TABLE "volunteers"
    ADD FOREIGN KEY ("group_name") REFERENCES "volunteer_groups" (group_name);

CREATE TABLE "administrators"
(
    "username"     TEXT PRIMARY KEY,
    "password"     TEXT NOT NULL,
    "phone_number" TEXT NOT NULL
);

CREATE TABLE "announcements"
(
    "id"               BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"            TEXT   NOT NULL,
    "status"           TEXT   NOT NULL,
    "content"          TEXT   NOT NULL,
    "reward"           BIGINT NOT NULL,
    "creation_date"    DATE DEFAULT current_date,
    "expiration_date"  DATE DEFAULT current_date + INTERVAL '5 days',
    "author"           TEXT   NOT NULL,
    "volunteer_groups" TEXT   NULL
);

CREATE TABLE "invite_keys"
(
    "code"      TEXT PRIMARY KEY,
    "activated" BOOLEAN DEFAULT FALSE
);

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("author") REFERENCES "administrators" ("username");

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("volunteer_groups") REFERENCES "volunteer_groups" ("group_name");

CREATE INDEX idx_announcements_title ON announcements ("title");
