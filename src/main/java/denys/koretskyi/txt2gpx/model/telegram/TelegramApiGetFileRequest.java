package denys.koretskyi.txt2gpx.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramApiGetFileRequest {

    @JsonProperty("file_id")
    private String fileId;
}
