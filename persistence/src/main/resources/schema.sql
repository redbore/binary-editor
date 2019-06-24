CREATE TABLE IF NOT EXISTS specification(
    id UUID PRIMARY KEY,
    name TEXT,
    body BLOB
);

CREATE TABLE IF NOT EXISTS binary_file(
    id UUID PRIMARY KEY,
    name TEXT,
    body BLOB
);

CREATE TABLE IF NOT EXISTS segment(
    id UUID PRIMARY KEY,
    specification_id UUID REFERENCES specification(id),
    name TEXT,
    count_link TEXT
);

CREATE TABLE IF NOT EXISTS field_description(
    id UUID PRIMARY KEY,
    segment_id UUID REFERENCES segment(id),
    name TEXT,
    type TEXT,
    length_link TEXT
);

CREATE TABLE IF NOT EXISTS field(
    id UUID PRIMARY KEY,
    binary_file_id UUID REFERENCES binary_file(id),
    field_description_id UUID REFERENCES field_description(id),
    value TEXT,
    length INTEGER
);
