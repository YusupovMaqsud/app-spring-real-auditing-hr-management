package uz.pdp.appspringrealauditinghrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringrealauditinghrmanagement.payload.ApiResponse;
import uz.pdp.appspringrealauditinghrmanagement.payload.TourniquetCardDto;
import uz.pdp.appspringrealauditinghrmanagement.service.TourniquetCardService;

import java.util.UUID;

@RestController
@RequestMapping("/api/tourniquetcard")
public class TourniquetCardController {
    @Autowired
    TourniquetCardService tourniquetCardService;

    @PostMapping("/create")
    public HttpEntity<?> createCard(@RequestBody TourniquetCardDto tourniquetCardDto){
        ApiResponse apiResponse = tourniquetCardService.addCard(tourniquetCardDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @PutMapping("/edet")
    public HttpEntity<?> edetCard(@RequestBody TourniquetCardDto tourniquetCardDto, @PathVariable UUID id){
        ApiResponse edet = tourniquetCardService.edet(tourniquetCardDto, id);
        return ResponseEntity.status(edet.isSuccess()?202:409).body(edet);
    }

    @GetMapping("/checked")
    public HttpEntity<?> checkedCard(@PathVariable UUID id){
        ApiResponse apiResponse = tourniquetCardService.checkedCard(id);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

}
