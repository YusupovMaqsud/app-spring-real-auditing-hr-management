package uz.pdp.appspringrealauditinghrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourniquetCardDto {

    @NotNull
    private UUID compId;

    @NotNull
    private UUID employeeId;
}
