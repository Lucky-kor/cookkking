-- Member 더미 데이터 (member_id = 1)
INSERT INTO member (
    login_id,
    email,
    nick_name,
    password,
    phone_number,
    profile,
    title,
    rice_point,
    member_status,
    created_at,
    modified_at
) VALUES (
    'a',
    'a@a.a',
    '택택',
    '{bcrypt}$2a$10$6aygq.9x2nV/LovgTEoIXO2D/jj.kWajHSVoHXLGUVRk50DhinRya', -- "qwerasdf1!"
    '010-1111-2222',
    'https://example.com/profile1.png',
    '요리왕',
    300,
    'MEMBER_ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ROLE (ElementCollection은 별도 테이블로 관리됨, 기본값 "ROLE_USER")
INSERT INTO member_roles (member_member_id, roles) VALUES (1, 'USER');
--
-- INSERT INTO menu_category (
--     menu_category_id,
--     menu_category_name
-- ) VALUES
-- (1, '한식'),
-- (2, '중식'),
-- (3, '일식'),
-- (4, '양식'),
-- (5, '기타');
--
-- INSERT INTO menu (
--     menu_id,
--     menu_name,
--     description,
--     image,
--     menu_category_id,
--     created_at,
--     modified_at
-- ) VALUES
-- (1, '김치찌개', '얼큰한 김치로 끓인 찌개', 'https://example.com/image1.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (2, '계란찜', '부드러운 달걀찜', 'https://example.com/image2.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (3, '미역국', '소고기와 미역의 조화', 'https://example.com/image3.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (4, '김밥', '간편한 한끼 도시락', 'https://example.com/image4.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (5, '닭볶음탕', '매콤한 닭 요리', 'https://example.com/image5.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (6, '스팸마요덮밥', '스팸과 마요의 만남', 'https://example.com/image6.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (7, '소세지야채볶음', '아이들이 좋아하는 반찬', 'https://example.com/image7.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (8, '된장찌개', '구수한 전통 찌개', 'https://example.com/image8.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (9, '오므라이스', '계란으로 덮은 볶음밥', 'https://example.com/image9.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (10, '카레', '향신료 가득한 인기 메뉴', 'https://example.com/image10.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
-- INSERT INTO recipe_board (
--     recipe_board_id,
--     title,
--     menu_name,
--     content,
--     image,
--     recipe_time,
--     recipe_status,
--     recipe_board_status,
--     member_id,
--     menu_id,
--     created_at,
--     modified_at
-- ) VALUES
-- (1, '간단한 김치찌개 레시피', '김치찌개', '맛있는 김치찌개 끓이는 법', 'https://example.com/image1.jpg', '30분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (2, '초간단 계란찜 만들기', '계란찜', '계란 3개만으로 부드러운 찜 완성', 'https://example.com/image2.jpg', '10분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (3, '소고기 미역국 황금 레시피', '미역국', '미역과 고기 양 조절이 포인트!', 'https://example.com/image3.jpg', '40분', 'RECIPE_PRIVATE', 'RECIPE_BOARD_POST', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (4, '참치김밥 만들기', '김밥', '도시락 필수 메뉴 참치김밥 레시피', 'https://example.com/image4.jpg', '25분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (5, '닭볶음탕 매콤하게 끓이기', '닭볶음탕', '매콤 달달한 양념이 포인트', 'https://example.com/image5.jpg', '45분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (6, '스팸마요덮밥 뚝딱 완성!', '스팸마요덮밥', '혼밥에 딱 좋은 레시피', 'https://example.com/image6.jpg', '15분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (7, '소세지야채볶음 간단 반찬', '소세지야채볶음', '밥도둑 반찬 레시피', 'https://example.com/image7.jpg', '20분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (8, '된장찌개 고소하고 깊은맛', '된장찌개', '집밥 필수 찌개 레시피', 'https://example.com/image8.jpg', '30분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (9, '오므라이스 부드럽고 고소하게', '오므라이스', '계란이 반쯤 익었을 때가 포인트!', 'https://example.com/image9.jpg', '20분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- (10, '카레라이스 정석 레시피', '카레', '남녀노소 좋아하는 국민 요리', 'https://example.com/image10.jpg', '35분', 'RECIPE_PUBLIC', 'RECIPE_BOARD_POST', 1, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
--
-- INSERT INTO recipe_step (
--     recipe_step_id, description, image, stem_number, recipe_board_id
-- ) VALUES
-- (1, '계란을 풀어줍니다.', 'https://example.com/step_image1.jpg', 1, 1),
-- (2, '계란을 풀어줍니다.', 'https://example.com/step_image2.jpg', 2, 1),
-- (3, '양파를 채 썰어줍니다.', 'https://example.com/step_image3.jpg', 3, 1),
-- (4, '그릇에 담아 마무리합니다.', 'https://example.com/step_image4.jpg', 1, 2),
-- (5, '그릇에 담아 마무리합니다.', 'https://example.com/step_image5.jpg', 2, 2),
-- (6, '예쁘게 플레이팅 합니다.', 'https://example.com/step_image6.jpg', 3, 2),
-- (7, '계란을 풀어줍니다.', 'https://example.com/step_image7.jpg', 1, 3),
-- (8, '고기를 넣고 익힙니다.', 'https://example.com/step_image8.jpg', 2, 3),
-- (9, '재료를 준비합니다.', 'https://example.com/step_image9.jpg', 3, 3),
-- (10, '김치를 넣고 볶아줍니다.', 'https://example.com/step_image10.jpg', 1, 4),
-- (11, '재료를 준비합니다.', 'https://example.com/step_image11.jpg', 2, 4),
-- (12, '밥 위에 얹어줍니다.', 'https://example.com/step_image12.jpg', 3, 4),
-- (13, '김치를 넣고 볶아줍니다.', 'https://example.com/step_image13.jpg', 1, 5),
-- (14, '김치를 넣고 볶아줍니다.', 'https://example.com/step_image14.jpg', 2, 5),
-- (15, '예쁘게 플레이팅 합니다.', 'https://example.com/step_image15.jpg', 3, 5),
-- (16, '밥 위에 얹어줍니다.', 'https://example.com/step_image16.jpg', 1, 6),
-- (17, '계란을 풀어줍니다.', 'https://example.com/step_image17.jpg', 2, 6),
-- (18, '예쁘게 플레이팅 합니다.', 'https://example.com/step_image18.jpg', 3, 6),
-- (19, '밥 위에 얹어줍니다.', 'https://example.com/step_image19.jpg', 1, 7),
-- (20, '예쁘게 플레이팅 합니다.', 'https://example.com/step_image20.jpg', 2, 7),
-- (21, '재료를 준비합니다.', 'https://example.com/step_image21.jpg', 3, 7),
-- (22, '재료를 준비합니다.', 'https://example.com/step_image22.jpg', 1, 8),
-- (23, '고기를 넣고 익힙니다.', 'https://example.com/step_image23.jpg', 2, 8),
-- (24, '그릇에 담아 마무리합니다.', 'https://example.com/step_image24.jpg', 3, 8),
-- (25, '예쁘게 플레이팅 합니다.', 'https://example.com/step_image25.jpg', 1, 9),
-- (26, '고기를 넣고 익힙니다.', 'https://example.com/step_image26.jpg', 2, 9),
-- (27, '밥 위에 얹어줍니다.', 'https://example.com/step_image27.jpg', 3, 9),
-- (28, '고기를 넣고 익힙니다.', 'https://example.com/step_image28.jpg', 1, 10),
-- (29, '김치를 넣고 볶아줍니다.', 'https://example.com/step_image29.jpg', 2, 10),
-- (30, '재료를 준비합니다.', 'https://example.com/step_image30.jpg', 3, 10);
