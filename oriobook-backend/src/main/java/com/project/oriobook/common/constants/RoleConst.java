package com.project.oriobook.common.constants;

public class RoleConst {
    // PreAuthorize
    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String ROLE_USER = "hasRole('ROLE_USER')";
    public static final String ROLE_ADMIN_USER = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')";

    // API Operations
    public static final String OP_ADMIN = "[ADMIN]";
    public static final String OP_USER = "[USER]";
    public static final String OP_ADMIN_USER = "[ADMIN, USER]";
}
