package com.jotave.todolist.services.validations;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
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
