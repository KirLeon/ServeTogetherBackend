CREATE TABLE "market"
(
    "id"         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"      BIGINT NOT NULL,
    "price"      BIGINT NOT NULL,
    "image_path" TEXT   NOT NULL
);

CREATE TABLE "volunteer_group"
(
    "group_name"           TEXT UNIQUE,
    "username"             TEXT UNIQUE,
    "active_ann_quantity"  INTEGER NOT NULL,
    "pending_ann_quantity" INTEGER NOT NULL
);

ALTER TABLE "volunteer_group"
    ADD PRIMARY KEY ("group_name", "username");

CREATE TABLE "announcements"
(
    "id"              BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title"           TEXT   NOT NULL,
    "status"          TEXT   NOT NULL,
    "content"         TEXT   NOT NULL,
    "reward"          BIGINT NOT NULL,
    "creation_date"   DATE DEFAULT current_date,
    "expiration_date" DATE DEFAULT current_date + INTERVAL '5 days',
    "author_id"       TEXT   NOT NULL,
    "volunteer_group" TEXT NULL
);


CREATE TABLE "administrators"
(
    "username" TEXT PRIMARY KEY,
    "password" TEXT NOT NULL
);

CREATE TABLE "volunteers"
(
    "username"     TEXT PRIMARY KEY,
    "password"     TEXT   NOT NULL,
    "phone_number" TEXT   NOT NULL UNIQUE,
    "coin_amount"  BIGINT NOT NULL
);

ALTER TABLE "volunteer_group"
    ADD FOREIGN KEY ("username") REFERENCES "volunteers" (username);

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("author_id") REFERENCES "administrators" ("username");

ALTER TABLE "announcements"
    ADD FOREIGN KEY ("volunteer_group") REFERENCES "volunteer_group" ("group_name");

CREATE INDEX idx_announcements_title ON announcements ("title");
