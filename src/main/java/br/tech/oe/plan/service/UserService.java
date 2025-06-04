package br.tech.oe.plan.service;

import br.tech.oe.plan.controller.v1.filters.UserFilter;
import br.tech.oe.plan.dto.user.UserDTO;

public interface UserService extends BaseService<UserDTO, UserFilter> {
}