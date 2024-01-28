package denys.koretskyi.txt2gpx.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramApiGetFileResponse {

    private String ok;
    private TelegramApiGetFileResponseResult result;
}
