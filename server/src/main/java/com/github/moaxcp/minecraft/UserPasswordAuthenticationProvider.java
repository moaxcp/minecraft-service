package com.github.moaxcp.minecraft;

import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class UserPasswordAuthenticationProvider<T> implements AuthenticationProvider<T> {

    @Override
    public Publisher<AuthenticationResponse> authenticate(T httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            if (authenticationRequest.getIdentity().equals("user") && authenticationRequest.getSecret().equals("password")) {
                emitter.success(AuthenticationResponse.success("user"));
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}
