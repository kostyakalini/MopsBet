package by.mops.bet.validator;

import by.mops.bet.model.User;
import by.mops.bet.model.UserBet;
import by.mops.bet.security.MyUserDetails;
import by.mops.bet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BalanceValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserBet.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserBet userBet = (UserBet) o;

        MyUserDetails myUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "Required", "This field is required.");
        if (userBet.getValue() > myUser.getUser().getBalance()) {
            errors.rejectValue("value", "Size.userBet.value", "Недостаточно средств.");
        }
    }
}
