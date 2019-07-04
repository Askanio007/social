package com.social.server.service;

import com.social.server.dao.DialogRepository;
import com.social.server.entity.User;
import com.social.server.service.impl.DialogServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class DialogServiceTest {

    private DialogRepository repository = Mockito.mock(DialogRepository.class);
    private DialogServiceImpl dialogService = new DialogServiceImpl(repository);

    @Test
    public void successCreateDialog() {
        Mockito.when(repository.existsByUsersIdIn(any())).thenReturn(false);
        dialogService.create(new User());
        Mockito.verify(repository, times(1)).save(any());
    }

    @Test
    public void failedCreateDialog() {
        Mockito.when(repository.existsByUsersIdIn(any())).thenReturn(true);
        dialogService.create(new User());
        Mockito.verify(repository, times(0)).save(any());
    }
}
