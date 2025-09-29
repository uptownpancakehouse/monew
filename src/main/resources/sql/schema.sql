-- ========================
-- Users (사용자)
-- ========================
CREATE TABLE users (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        email VARCHAR(255) NOT NULL UNIQUE,
        nickname VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        deleted BOOLEAN DEFAULT FALSE
);

-- ========================
-- Interests (관심사)
-- ========================
CREATE TABLE interests (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        name VARCHAR(255) NOT NULL UNIQUE,
        subscriber_count BIGINT DEFAULT 0
);

-- ========================
-- KEYywords (키워드)
-- ========================
CREATE TABLE keywords (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        keyword VARCHAR(255) NOT NULL UNIQUE
);

-- ========================
-- InterestKeywords (관심사-키워드, 다대다 매핑)
-- ========================
CREATE TABLE interest_keywords (
        interest_id UUID NOT NULL,
        keyword_id UUID NOT NULL,
        PRIMARY KEY (interest_id, keyword_id),
        FOREIGN KEY (interest_id) REFERENCES interests(id) ON DELETE CASCADE,
        FOREIGN KEY (keyword_id) REFERENCES keywords(id) ON DELETE CASCADE
);

-- ========================
-- UserInterests (사용자-관심사)
-- ========================
CREATE TABLE user_interests (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        user_id UUID NOT NULL,
        interest_id UUID NOT NULL,
        subscribed_by_me BOOLEAN DEFAULT FALSE,
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        FOREIGN KEY (interest_id) REFERENCES interests(id) ON DELETE CASCADE,
        UNIQUE (user_id, interest_id)
);

-- ========================
-- Articles (뉴스 기사)
-- ========================
CREATE TABLE articles (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        source VARCHAR(255),
        source_url TEXT,
        title VARCHAR(500) NOT NULL,
        publish_date TIMESTAMP,
        summary TEXT,
        comment_count INT DEFAULT 0,
        view_count BIGINT DEFAULT 0,
        deleted BOOLEAN DEFAULT FALSE
);

-- ========================
-- UserArticles (사용자-기사)
-- ========================
CREATE TABLE user_articles (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        user_id UUID NOT NULL,
        article_id UUID NOT NULL,
        viewed_by_me BOOLEAN DEFAULT FALSE,
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
        UNIQUE (user_id, article_id)
);

-- ========================
-- Comments (댓글)
-- ========================
CREATE TABLE comments (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        article_id UUID NOT NULL,
        user_id UUID NOT NULL,
        content TEXT NOT NULL,
        like_count INT DEFAULT 0,
        liked_by_me BOOLEAN DEFAULT FALSE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        deleted BOOLEAN DEFAULT FALSE,
        FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ========================
-- User Comment Like (사용자-좋아요한 댓글)
-- ========================
CREATE TABLE user_comment_like (
        user_id UUID NOT NULL,
        comment_id UUID NOT NULL,
        liked_by_me BOOLEAN DEFAULT FALSE,
        PRIMARY KEY (user_id, comment_id),
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

-- ========================
-- Notifications (알림)
-- ========================
CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,        --FK
    content TEXT,
    resource_type VARCHAR(100),
    resource_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- ========================
-- Users (사용자)
-- ========================
INSERT INTO users (id, email, nickname, password)
VALUES
    (gen_random_uuid(), 'alice@example.com', 'Alice', 'password1'),
    (gen_random_uuid(), 'bob@example.com', 'Bob', 'password2'),
    (gen_random_uuid(), 'charlie@example.com', 'Charlie', 'password3');

-- ========================
-- Interests (관심사)
-- ========================
INSERT INTO interests (id, name, subscriber_count)
VALUES
    (gen_random_uuid(), 'Technology', 10),
    (gen_random_uuid(), 'Finance', 5),
    (gen_random_uuid(), 'Sports', 7);

-- ========================
-- Keywords (키워드)
-- ========================
INSERT INTO keywords (id, keyword)
VALUES
    (gen_random_uuid(), 'AI'),
    (gen_random_uuid(), 'Blockchain'),
    (gen_random_uuid(), 'Football');

-- ========================
-- InterestKeywords (관심사-키워드)
-- 예: Technology → AI, Blockchain / Sports → Football
-- ========================
INSERT INTO interest_keywords (interest_id, keyword_id)
SELECT i.id, k.id FROM interests i, keywords k
WHERE i.name = 'Technology' AND k.keyword = 'AI';

INSERT INTO interest_keywords (interest_id, keyword_id)
SELECT i.id, k.id FROM interests i, keywords k
WHERE i.name = 'Technology' AND k.keyword = 'Blockchain';

INSERT INTO interest_keywords (interest_id, keyword_id)
SELECT i.id, k.id FROM interests i, keywords k
WHERE i.name = 'Sports' AND k.keyword = 'Football';

-- ========================
-- UserInterests (사용자-관심사)
-- ========================
INSERT INTO user_interests (id, user_id, interest_id, subscribed_by_me)
SELECT gen_random_uuid(), u.id, i.id, TRUE
FROM users u, interests i
WHERE u.nickname = 'Alice' AND i.name = 'Technology';

INSERT INTO user_interests (id, user_id, interest_id, subscribed_by_me)
SELECT gen_random_uuid(), u.id, i.id, TRUE
FROM users u, interests i
WHERE u.nickname = 'Bob' AND i.name = 'Finance';

-- ========================
-- Articles (뉴스 기사)
-- ========================
INSERT INTO articles (id, source, source_url, title, publish_date, summary, comment_count, view_count)
VALUES
    (gen_random_uuid(), 'NYTimes', 'https://nytimes.com/ai-news', 'AI is changing the world', now(), 'AI is transforming industries.', 0, 100),
    (gen_random_uuid(), 'Bloomberg', 'https://bloomberg.com/finance-news', 'Global markets rally', now(), 'Markets are recovering.', 0, 50);

-- ========================
-- UserArticles (사용자-기사)
-- ========================
INSERT INTO user_articles (id, user_id, article_id, viewed_by_me)
SELECT gen_random_uuid(), u.id, a.id, TRUE
FROM users u, articles a
WHERE u.nickname = 'Alice' AND a.title = 'AI is changing the world';

INSERT INTO user_articles (id, user_id, article_id, viewed_by_me)
SELECT gen_random_uuid(), u.id, a.id, FALSE
FROM users u, articles a
WHERE u.nickname = 'Bob' AND a.title = 'Global markets rally';

-- ========================
-- Comments (댓글)
-- ========================
INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'Great article!', 3
FROM articles a, users u
WHERE a.title = 'AI is changing the world' AND u.nickname = 'Alice';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 1
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Bob';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 0
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Alice';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 0
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Bob';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 0
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Bob';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 0
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Alice';

INSERT INTO comments (id, article_id, user_id, content, like_count)
SELECT gen_random_uuid(), a.id, u.id, 'I totally agree!', 0
FROM articles a, users u
WHERE a.title = 'Global markets rally' AND u.nickname = 'Bob';

-- ========================
-- User Comment Like (사용자-좋아요한 댓글)
-- ========================
INSERT INTO user_comment_like (user_id, comment_id, liked_by_me)
SELECT u.id, c.id, TRUE
FROM users u, comments c
WHERE u.nickname = 'Charlie' AND c.content = 'Great article!';

-- ========================
-- Notifications (알림)
-- ========================
INSERT INTO notifications (id, user_id, content, resource_type, resource_id)
SELECT gen_random_uuid(), u.id, 'You have a new comment!', 'ARTICLE', a.id
FROM users u, articles a
WHERE u.nickname = 'Alice' AND a.title = 'AI is changing the world';
