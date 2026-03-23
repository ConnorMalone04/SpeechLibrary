INSERT INTO topic (id, name) VALUES (1, 'AI');
INSERT INTO person (id, name) VALUES (1, 'Alan Turing');

INSERT INTO speech (id, title, content, audio_url, person_id, topic_id)
VALUES (1, 'Test Speech', 'Test content', null, 1, 1);