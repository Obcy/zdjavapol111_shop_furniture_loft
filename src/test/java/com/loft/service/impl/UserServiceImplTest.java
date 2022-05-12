package com.loft.service.impl;

import com.loft.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnTrueWhenUserByEmailAddressExists() {
        //given
        Mockito.when(userRepository.existsByEmailAddress("test@test.com")).thenReturn(true);
        //when
        Boolean result = userService.existsByEmailAddress("test@test.com");
        //then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenUserByEmailAddressNotExists() {
        //given
        Mockito.when(userRepository.existsByEmailAddress("test@test.com")).thenReturn(false);
        //when
        Boolean result = userService.existsByEmailAddress("test@test.com");
        //then
        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenCheckIfExistWithEmailAddressIsNullOrEmpty() {
        //given

        //when & then
        assertThatThrownBy(() -> userService.existsByEmailAddress(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> userService.existsByEmailAddress(""))
                .isInstanceOf(IllegalArgumentException.class);

    }



}