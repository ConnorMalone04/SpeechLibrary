import os

output_path = "src/main/resources/data-test.sql"

topics = [
    (1, "AI"),
    (2, "History"),
    (3, "Politics"),
    (4, "Science"),
]

people = [
    (1, "Alan Turing", 1),
    (2, "Ada Lovelace", 1),
    (3, "Abraham Lincoln", 2),
    (4, "Marie Curie", 4),
]

os.makedirs(os.path.dirname(output_path), exist_ok=True)

with open(output_path, "w") as f:
    # Topics
    for tid, name in topics:
        f.write(f"INSERT INTO topic (id, name) VALUES ({tid}, '{name}');\n")

    # People
    for pid, name, topic_id in people:
        f.write(
            f"INSERT INTO person (id, name, topic_id) VALUES ({pid}, '{name}', {topic_id});\n"
        )

    # Speeches
    speech_id = 1

    # num of speeches scaled via workflow change
    NUM_SPEECHES = int(os.getenv("NUM_SPEECHES", 2000))

    for i in range(NUM_SPEECHES):
        person_id = (i % len(people)) + 1
        topic_id = people[person_id - 1][2]

        title = f"Test Speech {speech_id}"
        content = ("Lorem ipsum " * 20).replace("'", "''")

        date = f"2020-01-{(i % 28) + 1:02d}"

        f.write(
            "INSERT INTO speech (id, title, content, audio_url, person_id, topic_id, date) "
            f"VALUES ({speech_id}, '{title}', '{content}', null, {person_id}, {topic_id}, '{date}');\n"
        )

        speech_id += 1

print(f"Generated {NUM_SPEECHES} speeches into {output_path}")