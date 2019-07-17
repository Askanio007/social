package com.social.server.service;

import com.social.server.dao.DialogRepository;
import com.social.server.entity.Dialog;
import com.social.server.entity.User;
import com.social.server.service.impl.DialogServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DialogServiceTest {

    private DialogRepository repository = Mockito.mock(DialogRepository.class);
    private UserService userService = Mockito.mock(UserService.class);
    private DialogServiceImpl dialogService = new DialogServiceImpl(repository, userService);

    @Test
    public void successCreateDialog() {
        dialogService.create(Arrays.asList(new User()));
        verify(repository, times(1)).save(any());
    }

    @Test
    public void successGettingDialog() {
        List<Long> usersId = Arrays.asList(0L);
        when(repository.findOneByUsersId(new HashSet<>(usersId))).thenReturn(new Dialog());
        dialogService.create(Arrays.asList(new User()));
        verify(repository, times(0)).save(any());
        verify(repository, times(1)).findOneByUsersId(any(HashSet.class));
    }

    @Test
    public void successGetDialogCreate() {
        List<Long> usersId = Arrays.asList(1L, 2L);
        when(userService.getById(1L)).thenReturn(new User());
        when(userService.getById(2L)).thenReturn(new User());
        when(repository.findOneByUsersId(new HashSet<>(usersId))).thenReturn(null);
        dialogService.getDialogBy(usersId);
        verify(repository, times(1)).findOneByUsersId(any(HashSet.class));
    }

    @Test
    public void successGetDialogFind() {
        List<Long> usersId = Arrays.asList(1L, 2L);
        when(repository.findOneByUsersId(new HashSet<>(usersId))).thenReturn(new Dialog());
        dialogService.getDialogBy(usersId);
        verify(repository, times(1)).findOneByUsersId(any(HashSet.class));
    }
}
