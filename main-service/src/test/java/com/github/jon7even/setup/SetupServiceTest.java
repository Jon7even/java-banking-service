package com.github.jon7even.setup;

import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.repository.BankAccountRepository;
import com.github.jon7even.repository.UserEmailRepository;
import com.github.jon7even.repository.UserPhoneRepository;
import com.github.jon7even.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SetupServiceTest extends PreparationObjectsForTests {
    @Mock protected UserRepository userRepository;
    @Mock protected UserEmailRepository userEmailRepository;
    @Mock protected UserPhoneRepository userPhoneRepository;
    @Mock protected BankAccountRepository bankAccountRepository;
    @Mock protected UserMapper userMapper;
}
