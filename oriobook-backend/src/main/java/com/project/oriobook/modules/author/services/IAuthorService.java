package com.project.oriobook.modules.author.services;

import com.project.oriobook.modules.author.entities.Author;

public interface IAuthorService {
    Author getAuthorById(String id) throws Exception;
}
