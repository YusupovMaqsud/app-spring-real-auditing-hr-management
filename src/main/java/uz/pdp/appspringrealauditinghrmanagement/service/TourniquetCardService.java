package uz.pdp.appspringrealauditinghrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appspringrealauditinghrmanagement.entity.Company;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetCard;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetCardDto;
import uz.pdp.appspringrealauditinghrmanagement.repository.CompanyRepository;
import uz.pdp.appspringrealauditinghrmanagement.repository.EmployeeRepository;
import uz.pdp.appspringrealauditinghrmanagement.repository.TourniquetCardRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TourniquetCardService {
    @Autowired
    TourniquetCardRepository tourniquetCardRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    public ApiResponse addCard(TourniquetCardDto tourniquetCardDto){
        Optional<Employee> optionalEmployee = employeeRepository.findById(tourniquetCardDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee topilmadi", false);

        Optional<Company> optionalCompany = companyRepository.findById(tourniquetCardDto.getCompId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company topilmadi", false);
        Company company = optionalCompany.get();
        Employee employee = optionalEmployee.get();

        TourniquetCard tourniquetCard = new TourniquetCard();
        tourniquetCard.setEmployee(employee);
        tourniquetCard.setCompany(company);
        tourniquetCardRepository.save(tourniquetCard);
        return new ApiResponse(" TournoquetCard added", true);
    }




    public ApiResponse edet(TourniquetCardDto tourniquetCardDto, UUID id){

        Optional<TourniquetCard> cardOptional = tourniquetCardRepository.findById(id);
        if (!cardOptional.isPresent())
            return new ApiResponse("TourniquetCard topilmadi", false);
        TourniquetCard tourniquetCard = cardOptional.get();

        Optional<Employee> optionalEmployee = employeeRepository.findById(tourniquetCardDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee topilmadi", false);
        Employee employee = optionalEmployee.get();
        Optional<Company> optionalCompany = companyRepository.findById(tourniquetCardDto.getCompId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company topilmadi", false);
        Company company = optionalCompany.get();

        tourniquetCard.setCompany(company);
        tourniquetCard.setEmployee(employee);
        tourniquetCardRepository.save(tourniquetCard);
        return new ApiResponse("Edded TourniquetCard", true);
    }




    public ApiResponse checkedCard(UUID id){
        Optional<TourniquetCard> cardOptional = tourniquetCardRepository.findById(id);
        if (!cardOptional.isPresent())
            return new ApiResponse("TourniquetCard topilmadi", false);
        TourniquetCard tourniquetCard = cardOptional.get();
        tourniquetCard.setStatus(false);
        tourniquetCardRepository.save(tourniquetCard);
        return new ApiResponse("Muvaffaqiyati tekshirildi", true);
    }
}
