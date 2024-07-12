package com.example.userSeguranca.application.userCase.update;

import com.example.userSeguranca.domain.entities.user.User;

public abstract class UpdeteUserEncoder implements UpdeteUser {
    protected abstract boolean decoder(String string);
    protected abstract boolean EncoderTrue(User infos);
}
