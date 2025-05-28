
CREATE TABLE users (
    id UUID PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE queues (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE queue_entries (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    queue_number INT NOT NULL,
    joined_at TIMESTAMP NOT NULL,
    called BOOLEAN DEFAULT FALSE,
    queue_id INT NOT NULL,
    FOREIGN KEY (queue_id) REFERENCES queues(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO queues (name, description)
VALUES 
    ('Dokter Umum', 'Layanan konsultasi umum dengan dokter'),
    ('Dokter Gigi', 'Layanan pemeriksaan dan tindakan gigi'),
    ('Laboratorium', 'Pemeriksaan laboratorium dan tes medis');