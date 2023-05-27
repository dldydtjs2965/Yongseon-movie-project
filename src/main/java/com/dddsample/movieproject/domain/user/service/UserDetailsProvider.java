package com.dddsample.movieproject.domain.user.service;

import com.dddsample.movieproject.domain.user.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsProvider {
    User getUserDetails();
}
