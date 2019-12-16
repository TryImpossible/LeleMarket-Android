package com.bynn.common.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 统一管理注解生命周期
 * SOURCE:在源文件中有效（即源文件保留）
 * CLASS:在class文件中有效（即class保留）
 * RUNTIME:在运行时有效（即运行时保留）
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
