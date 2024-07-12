package com.example.userSeguranca.application.userCase.save;

import com.example.userSeguranca.domain.entities.user.User;

public abstract class UserSaveEncoder implements UserSave{
    protected abstract boolean decoder(String string);
    protected abstract boolean EncoderTrue(User infos);
}
