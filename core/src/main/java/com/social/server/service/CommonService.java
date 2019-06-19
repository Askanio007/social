package com.social.server.service;

public interface CommonService<Class, ID> {
    Class findById(ID id);
}
