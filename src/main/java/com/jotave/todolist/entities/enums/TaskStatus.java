package com.jotave.todolist.entities.enums;

public enum TaskStatus {
    Completed(1),
    InProgress(2),
    Paused(3);

    private int code;

    private TaskStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static TaskStatus valueOf(int code){
        for(TaskStatus value : TaskStatus.values()){
            if(value.getCode() == code){
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid TaskStatus code");
    }
}
