package com.lms.coffeeshopclone.user.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 테스트")
    void saveTest(){
        //given
        final User user = new User("test");

        //when
        final User savedUser = userRepository.save(user);

        //then
        Assertions.assertEquals(savedUser.getUserId(), "test");
        Assertions.assertEquals(savedUser.getUserPoint(), 0L);
    }

}