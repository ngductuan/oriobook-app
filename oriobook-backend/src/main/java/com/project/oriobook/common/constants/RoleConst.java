package com.project.oriobook.common.constants;

import com.project.oriobook.common.enums.CommonEnum;

import java.util.List;

public class RoleConst {
    // PreAuthorize
    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String ROLE_USER = "hasRole('ROLE_USER')";
    public static final String ROLE_ADMIN_USER = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')";

}
