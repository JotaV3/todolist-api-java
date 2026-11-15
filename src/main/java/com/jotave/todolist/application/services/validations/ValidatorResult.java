package com.jotave.todolist.application.services.validations;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidatorResult {
    private final List<InvalidField> invalidFieldList;

    public ValidatorResult(List<InvalidField> invalidFieldList){
        this.invalidFieldList = invalidFieldList;
    }

    public static ValidatorResult newValidationResult(){
        return new ValidatorResult(new ArrayList<>());
    }

    public void addInvalidFieldToList(InvalidField invalidField){
        invalidFieldList.add(invalidField);
    }

    public boolean isInvalid(){
        return !invalidFieldList.isEmpty();
    }
}
