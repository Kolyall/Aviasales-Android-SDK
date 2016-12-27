package com.avia.application.module.annotations;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Nikolay Unuchek on 07.09.2016.
 */
@Scope
@Retention(RUNTIME)
public @interface ForApplication {
}
