package uz.pdp.appspringrealauditinghrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appspringrealauditinghrmanagement.entity.Company;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetCard;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetHistory;
import uz.pdp.appspringrealauditinghrmanagement.enums.TourniquetStatusEnum;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetCardDto;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetHistoryDto;
import uz.pdp.appspringrealauditinghrmanagement.repository.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourniquetHistoryService {
    @Autowired
    TourniquetHistoryRepository tourniquetHistoryRepository;
    @Autowired
    TourniquetCardRepository tourniquetCardRepository;
    @Autowired
    TourniquetStatusRepository tourniquetStatusRepository;

    public ApiResponse enter(TourniquetHistoryDto tourniquetHistoryDto){
        Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(tourniquetHistoryDto.getCardId());
        if (!optionalTourniquetCard.isPresent())
            return new ApiResponse("TourniquetCard topilmadi", false);
        TourniquetCard tourniquetCard = optionalTourniquetCard.get();
        TourniquetHistory history = new TourniquetHistory();
        history.setStatus(String.valueOf(tourniquetStatusRepository.findByName(TourniquetStatusEnum.ENTRY_SIDE)));
        history.setTourniquetCard(tourniquetCard);
        history.setEnteredAt(new Timestamp(System.currentTimeMillis()));
        tourniquetHistoryRepository.save(history);
        return new ApiResponse("Employee kirgan", true);
    }




    public ApiResponse exit(TourniquetHistoryDto tourniquetHistoryDto){
        Optional<TourniquetCard> optionalTourniquetCard = tourniquetCardRepository.findById(tourniquetHistoryDto.getCardId());
        if (!optionalTourniquetCard.isPresent())
            return new ApiResponse("TourniquetCard topilmadi", false);
        TourniquetCard tourniquetCard = optionalTourniquetCard.get();
        TourniquetHistory history = new TourniquetHistory();
        history.setStatus(String.valueOf(tourniquetStatusRepository.findByName(TourniquetStatusEnum.EXIT_SIDE)));
        history.setTourniquetCard(tourniquetCard);
        history.setExitedAt(new Timestamp(System.currentTimeMillis()));
        tourniquetHistoryRepository.save(history);
        return new ApiResponse("Employee chiqqan", true);
    }

}
