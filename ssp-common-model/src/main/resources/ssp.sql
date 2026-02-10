CREATE TABLE `sys_user` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                            `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名（登录用）',
                            `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
                            `role_id` BIGINT NULL COMMENT '角色ID',
                            `status` TINYINT DEFAULT 1 COMMENT '状态：1正常 0禁用',
                            `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
                            `creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
                            `updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

CREATE TABLE `sys_role` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                            `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
                            `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码（如admin/editor）',
                            `dept_id` BIGINT DEFAULT NULL COMMENT '部门ID（待定，关联部门表）',
                            `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
                            `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
                            `creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
                            `updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE `sys_menu` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
                            `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '上级ID 0-根节点',
                            `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单/按钮名称',
                            `icon` VARCHAR(50) DEFAULT NULL COMMENT '图标（菜单1有值）',
                            `type` TINYINT NOT NULL COMMENT '类型 1-菜单 2-按钮（核心区分）',
                            `front_path` VARCHAR(255) DEFAULT NULL COMMENT '前端组件路径（仅菜单1有值）',
                            `backend_route` VARCHAR(255) DEFAULT NULL COMMENT '后端路由/接口前缀（仅菜单1有值，如/api/user）',
                            `perm_code` VARCHAR(100) DEFAULT NULL COMMENT '权限编码（按钮2为必填，如user:add/user:delete；菜单1可选，如user:list）',
                            `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
                            `status` TINYINT DEFAULT 1 COMMENT '状态：1正常 0禁用',
                            `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
                            `creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
                            `updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_parent_id` (`parent_id`),
                            KEY `idx_perm_code` (`perm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表（含按钮）';

CREATE TABLE `sys_menu_role` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
                                 `role_id` BIGINT NOT NULL COMMENT '角色ID',
                                 `menu_id` BIGINT NOT NULL COMMENT '菜单/按钮ID（关联sys_menu.id）',
                                 `status` TINYINT DEFAULT 1 COMMENT '状态：1正常 0禁用',
                                 `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
                                 `creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
                                 `updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`) COMMENT '角色-菜单/按钮唯一绑定，避免重复',
                                 KEY `idx_role_id` (`role_id`),
                                 KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单角色关联表';

CREATE TABLE `sys_user_role` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
                                 `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                 `role_id` BIGINT NOT NULL COMMENT '角色ID',
                                 `status` TINYINT DEFAULT 1 COMMENT '状态：1正常 0禁用',
                                 `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
                                 `creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
                                 `updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
                                 KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';