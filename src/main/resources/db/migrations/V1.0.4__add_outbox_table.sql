CREATE TABLE kafka_outbox
(
    id           BIGSERIAL                NOT NULL,
    operation    VARCHAR(20)              NOT NULL,
    aggregate    VARCHAR(255)             NOT NULL,
    key          VARCHAR(255)             NOT NULL,
    event        json                     NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT kafka_outbox_pk PRIMARY KEY (id)
);