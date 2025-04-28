CREATE TABLE `projectdb`.`post` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `author_id` INT NOT NULL ,
 `title` VARCHAR(255) NOT NULL ,
 `content` TEXT NOT NULL ,
 `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
 `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
 PRIMARY KEY (`id`),
 FOREIGN KEY (`author_id`) REFERENCES users (`id`)
) ENGINE = InnoDB;


CREATE TABLE users (
    -- 'id' column: Integer, serves as the primary key, automatically increments
    id INT AUTO_INCREMENT PRIMARY KEY,

    -- 'fname' column: Variable character string (up to 100 chars), cannot be null
    fname VARCHAR(100) NOT NULL,

    -- 'lname' column: Variable character string (up to 100 chars), can be null
    lname VARCHAR(100) NULL,

    -- 'email' column: Variable character string (up to 255 chars), cannot be null, must be unique
    email VARCHAR(255) NOT NULL UNIQUE,

    -- 'password' column: Variable character string (up to 255 chars), cannot be null
    -- Note: Store hashed passwords here, not plain text. 255 chars is usually sufficient for hashes.
    password VARCHAR(255) NOT NULL,

    -- 'bio' column: Text field for longer descriptions, can be null
    bio TEXT NULL,

    -- 'created_at' column: Timestamp, defaults to the time the row was created, cannot be null
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);