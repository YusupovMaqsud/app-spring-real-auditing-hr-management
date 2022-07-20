package uz.pdp.appspringrealauditinghrmanagement.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;

import java.util.Optional;
import java.util.UUID;

public class KimYozganiniBilish implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&  //null teng bo'lmasin
                authentication.isAuthenticated() && //sistemaga kirib turgan bo'lsin yani true bo'lsin
                !authentication.equals("anonymousUser")) {    //anonimUser bo'lmasin
            Employee employee = (Employee) authentication.getPrincipal();
            return Optional.of(employee.getId()); //userni idsini qaytaradi
        }
        return Optional.empty(); //Bo'sh qaytaradi
    }
}
