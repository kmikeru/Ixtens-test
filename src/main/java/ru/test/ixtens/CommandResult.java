package ru.test.ixtens;

import java.io.Serializable;

/** @author mike */
public class CommandResult implements Serializable{
    public Integer serial;
    public Object result;
    public Exception exception;
}
