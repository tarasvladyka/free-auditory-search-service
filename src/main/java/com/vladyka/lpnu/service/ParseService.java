package com.vladyka.lpnu.service;

import com.vladyka.lpnu.model.Group;
import com.vladyka.lpnu.model.Institute;

import java.io.IOException;
import java.util.List;

public interface ParseService {
    List<Institute> parseInstitutes() throws IOException;

    List<Group> parseGroups(Institute institute) throws IOException;
}
