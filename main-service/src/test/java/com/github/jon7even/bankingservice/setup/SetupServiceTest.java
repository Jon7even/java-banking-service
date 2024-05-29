package com.github.jon7even.bankingservice.setup;

import com.github.jon7even.bankingservice.mapper.UserMapper;
import com.github.jon7even.bankingservice.repository.BankAccountRepository;
import com.github.jon7even.bankingservice.repository.UserEmailRepository;
import com.github.jon7even.bankingservice.repository.UserPhoneRepository;
import com.github.jon7even.bankingservice.repository.UserRepository;
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
