package com.cc.journalApp.service;

import com.cc.journalApp.models.User;
import com.cc.journalApp.request.UserRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(new UserRequest("abcd17", "abcd")),
                Arguments.of(new UserRequest("xyz15", "asdfhj"))
        );
    }
}
