package ru.kata.spring.boot_security.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;

@Component
public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String name) {
        if (name.isEmpty()) {
            return null;
        }
        return new Role(name);
    }
}
