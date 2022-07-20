package uz.pdp.appspringrealauditinghrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appspringrealauditinghrmanagement.entity.Task;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetHistory;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoDto {
    private List<Task> taskList;

    private List<TourniquetHistory> tourniquetHistories;



}
